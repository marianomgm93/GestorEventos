package service;

import Utilidades.UtilidadesGenerales;
import Utilidades.Validacion;
import exceptions.ContraseniaInvalidaException;
import exceptions.EmailInvalidoException;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class VendedorService {

    public void nuevoTicket(Scanner sc, Vendedor vendedor, Boleteria boleteria, String archivo) {
        StringBuilder sb = new StringBuilder();
        System.out.println(boleteria.mostrarEventos());

        boolean flag = false;
        String eventoId;
        Evento evento = null;

        // ================== SELECCIÓN DE EVENTO (solo activos) ==================
        do {
            System.out.println("Ingrese el id del evento o \"S\" para salir");
            eventoId = sc.nextLine().trim();

            if (eventoId.equalsIgnoreCase("s")) {
                System.out.println("...Saliendo...");
                return;
            }

            evento = null;
            for (Evento e : boleteria.getEventos().getElementos()) {
                if (String.valueOf(e.getId()).equals(eventoId)) {
                    if (e.isActivo()) {
                        evento = e;
                        flag = true;
                    } else {
                        System.out.println("Este evento ya no está activo. No se pueden vender tickets.");
                    }
                    break;
                }
            }

            if (!flag) {
                System.out.println("El número ingresado es inválido o el evento no está disponible.");
            } else if (evento.getFuncionesDisponibles().isEmpty()) {
                System.out.println("El evento seleccionado no tiene funciones disponibles");
                flag = false;
                evento = null;
            }

        } while (!flag);

        // ================== SELECCIÓN DE FUNCIÓN ==================
        sb.setLength(0);
        sb.append("/////////////////////// Funciones ///////////////////////\n");
        for (Funcion f : evento.getFunciones()) {
            sb.append("id: ").append(f.getId())
                    .append("\tFecha: ").append(UtilidadesGenerales.formatearFecha(f.getFechayHora()))
                    .append("\tRecinto: ").append(f.getRecinto().getNombre())
                    .append("\tDisponibilidad: ").append(f.cantidadAsientosDisponibles()).append("\n");
        }
        sb.append("/////////////////////// Fin funciones ///////////////////////");
        System.out.println(sb);

        flag = false;
        String funcionId;
        Funcion funcion = null;

        do {
            System.out.println("Ingrese el id de la función o \"S\" para salir");
            funcionId = sc.nextLine().trim();

            if (funcionId.equalsIgnoreCase("s")) {
                System.out.println("...Saliendo...");
                return;
            }

            for (Funcion f : evento.getFunciones()) {
                if (String.valueOf(f.getId()).equals(funcionId)) {
                    funcion = f;
                    flag = true;
                    break;
                }
            }
            if (!flag) System.out.println("El número ingresado es inválido, inténtelo nuevamente");
        } while (!flag);

        // ================== SELECCIÓN DE SECTOR ==================
        System.out.println(funcion.asientosDisponibles());

        flag = false;
        Sector sector = null;
        String capturar;

        do {
            System.out.println("Ingrese el número del sector o \"S\" para salir");
            capturar = sc.nextLine().trim();

            if (capturar.equalsIgnoreCase("s")) {
                System.out.println("...Saliendo...");
                return;
            }

            for (Sector s : funcion.getRecinto().getSectores()) {
                if (String.valueOf(s.getId()).equals(capturar)) {
                    sector = s;
                    flag = true;
                    break;
                }
            }
            if (!flag) System.out.println("Debe ingresar un sector válido");
        } while (!flag);

        // ================== OCUPAR ASIENTO ==================
        Asiento asiento = null;

        if (sector.isTieneAsientos()) {
            flag = false;
            do {
                System.out.println(sector.verAsientosDisponibles());
                System.out.println("Ingrese el número del asiento o \"S\" para salir");
                capturar = sc.nextLine().trim();

                if (capturar.equalsIgnoreCase("s")) {
                    System.out.println("...Saliendo...");
                    return;
                }

                for (Asiento a : sector.getAsientos()) {
                    if (String.valueOf(a.getId()).equals(capturar) && a.isDisponible()) {
                        asiento = a;
                        flag = true;
                        break;
                    }
                }
                if (!flag) System.out.println("El número ingresado es inválido o ya está ocupado, inténtelo nuevamente");
            } while (!flag);

        } else {
            for (Asiento a : sector.getAsientos()) {
                if (a.isDisponible()) {
                    asiento = a;
                    break;
                }
            }
            if (asiento == null) {
                System.out.println("No hay más capacidad en este sector.");
                return;
            }
        }

        // ================== CREAR TICKET ==================
        asiento.setDisponible(false);

        double precio = funcion.getPrecioBase() + sector.getValorExtra();

        Ticket ticket = new Ticket(
                funcion.getRecinto().getDireccion(),
                asiento.getNumero(),
                evento.getId(),
                funcion.getId(),
                sector.getId(),
                evento.getNombre(),
                funcion.getFechayHora().toString(),
                precio
        );

        vendedor.getTicketsVendidos().add(ticket);
        boleteria.getVendidos().add(ticket);
        boleteria.guardarBoleteria(archivo);

        System.out.println("\n¡TICKET GENERADO CON ÉXITO!");
        System.out.println("ID del ticket: " + ticket.getId());
        System.out.println("Evento: " + evento.getNombre());
        System.out.println("Función: " + UtilidadesGenerales.formatearFecha(funcion.getFechayHora()));
        if (sector.isTieneAsientos()) {
            System.out.println("Asiento: " + asiento.getNumero());
        } else {
            System.out.println("Asiento general (sin numeración)");
        }
        System.out.println("Precio: $" + String.format("%.2f", precio));
    }

    // ====================== CREAR VENDEDOR (email único garantizado) ======================
    public void crearVendedor(Scanner sc, Boleteria boleteria, String archivo) {
        System.out.println("Ingrese nombre de vendedor");
        String nombre = sc.nextLine().trim();

        String email;
        boolean emailValido;
        do {
            System.out.println("Ingrese email");
            email = sc.nextLine().trim();
            emailValido = true;

            try {
                Validacion.validarEmail(email);
            } catch (EmailInvalidoException e) {
                System.out.println(e.getMessage());
                emailValido = false;
                continue;
            }

            for (Usuario u : boleteria.getUsuarios().getElementos()) {
                if (email.equalsIgnoreCase(u.getEmail())) {
                    System.out.println("El email ingresado ya está en uso");
                    emailValido = false;
                    break;
                }
            }
        } while (!emailValido);

        String contrasenia;
        do {
            System.out.println("Ingrese contraseña (mín. 8 caracteres, 1 letra y 1 número)");
            contrasenia = sc.nextLine();
            try {
                Validacion.validarContrasena(contrasenia);
                break;
            } catch (ContraseniaInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        Vendedor nuevo = new Vendedor(nombre, email, contrasenia);
        boleteria.guardarUsuario(nuevo, archivo);
        System.out.println("¡Vendedor creado correctamente! ID: " + nuevo.getId());
    }

    // ====================== VER MIS TICKETS (mejorado) ======================
    public void verMisTickets(Vendedor v, Boleteria boleteria) {
        if (v.getTicketsVendidos().isEmpty()) {
            System.out.println("No has vendido ningún ticket aún.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("/////////////////////// TUS TICKETS VENDIDOS ///////////////////////\n");
        sb.append("Total de tickets vendidos: ").append(v.getTicketsVendidos().size()).append("\n\n");

        for (Ticket t : v.getTicketsVendidos()) {
            Evento evento = boleteria.getEventos().buscarElementoId(t.getEventoId());
            String nombreEvento = evento != null ? evento.getNombre() : "[Evento no encontrado]";
            String estadoEvento = evento != null ? (evento.isActivo() ? "Activo" : "Dado de baja") : "Desconocido";

            Funcion funcion = evento != null ? evento.buscarFuncionPorId(t.getFuncionId()) : null;
            Sector sector = funcion != null ? funcion.buscarSectorPorId(t.getSectorId()) : null;

            sb.append("ID: ").append(t.getId())
                    .append(" | Evento: ").append(nombreEvento)
                    .append(" (").append(estadoEvento).append(")")
                    .append(" | Fecha: ").append(UtilidadesGenerales.formatearFecha(LocalDateTime.parse(t.getFechaYHora())))
                    .append(" | Precio: $").append(String.format("%.2f", t.getPrecio()));

            if (sector != null) {
                sb.append(" | Sector: ").append(sector.getNombre());
                if (sector.isTieneAsientos()) {
                    sb.append(" | Asiento: ").append(t.getAsiento());
                }
            }
            sb.append("\n");
        }

        sb.append("////////////////////////////////////////////////////////////////////\n");
        System.out.println(sb.toString());
    }

    public void calcularRecaudacion(Vendedor v) {
        if (v == null || v.getTicketsVendidos().isEmpty()) {
            System.out.println("No hay datos de tickets vendidos.");
            return;
        }

        double total = 0;
        for (Ticket t : v.getTicketsVendidos()) {
            total += t.getPrecio();
        }
        System.out.printf("Tu recaudación total es de: $%.2f%n", total);
    }
}