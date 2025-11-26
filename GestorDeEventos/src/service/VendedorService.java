package service;

import Utilidades.UtilidadesGenerales;
import Utilidades.Validacion;
import exceptions.ContraseniaInvalidaException;
import exceptions.EmailInvalidoException;
import model.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class VendedorService {

    private static final String LINEA = "═══════════════════════════════════════════════════════════════";
    private static final String SEP = "───────────────────────────────────────────────────────────────";

    public void nuevoTicket(Scanner sc, Vendedor vendedor, Boleteria boleteria, String archivo) {
        System.out.println(LINEA);
        System.out.println("                     VENTA DE TICKET");
        System.out.println(LINEA);
        System.out.println(boleteria.mostrarEventos());

        Evento evento = seleccionarEvento(sc, boleteria);
        if (evento == null) return;

        Funcion funcion = seleccionarFuncion(sc, evento);
        if (funcion == null) return;

        Sector sector = seleccionarSector(sc, funcion);
        if (sector == null) return;

        Asiento asiento = reservarAsiento(sc, sector);
        if (asiento == null) return;

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

        System.out.println(LINEA);
        System.out.println("                TICKET GENERADO CON ÉXITO");
        System.out.println(LINEA);
        System.out.println("ID del ticket      : " + ticket.getId());
        System.out.println("Evento             : " + evento.getNombre());
        System.out.println("Fecha y hora       : " + UtilidadesGenerales.formatearFecha(funcion.getFechayHora()));
        System.out.println("Recinto            : " + funcion.getRecinto().getNombre());
        System.out.println("Sector             : " + sector.getNombre());
        System.out.println(sector.isTieneAsientos() ? "Asiento            : " + asiento.getNumero()
                : "Tipo               : Entrada general");
        System.out.println("Precio final       : $" + String.format("%.2f", precio));
        System.out.println(LINEA);
    }

    private Evento seleccionarEvento(Scanner sc, Boleteria boleteria) {
        boolean flag;
        String entrada;
        Evento evento;
        do {
            System.out.print("Ingrese ID del evento (o S para salir): ");
            entrada = sc.nextLine().trim();
            if (entrada.equalsIgnoreCase("s")) {
                System.out.println("Operación cancelada.");
                return null;
            }
            evento = boleteria.getEventos().buscarElementoId(Integer.parseInt(entrada));
            if (evento == null) {
                System.out.println("No existe un evento con ese ID.");
                flag = true;
            } else if (!evento.isActivo()) {
                System.out.println("Este evento está dado de baja. No se pueden vender tickets.");
                flag = true;
            } else if (evento.getFunciones().isEmpty()) {
                System.out.println("Este evento no tiene funciones disponibles.");
                flag = true;
            } else {
                flag = false;
            }
        } while (flag);
        return evento;
    }

    private Funcion seleccionarFuncion(Scanner sc, Evento evento) {
        System.out.println(SEP);
        System.out.println("                  FUNCIONES DISPONIBLES");
        System.out.println(SEP);
        for (Funcion f : evento.getFunciones()) {
            System.out.printf("ID %-3d → %s | %s | Disponibles: %d | Precio base: $%.2f%n",
                    f.getId(),
                    UtilidadesGenerales.formatearFecha(f.getFechayHora()),
                    f.getRecinto().getNombre(),
                    f.cantidadAsientosDisponibles(),
                    f.getPrecioBase());
        }
        System.out.println(SEP);

        boolean flag;
        String entrada;
        Funcion funcion;
        do {
            System.out.print("Ingrese ID de la función (o S para salir): ");
            entrada = sc.nextLine().trim();
            if (entrada.equalsIgnoreCase("s")) {
                System.out.println("Operación cancelada.");
                return null;
            }
            funcion = null;
            for (Funcion f : evento.getFunciones()) {
                if (String.valueOf(f.getId()).equals(entrada)) {
                    funcion = f;
                    break;
                }
            }
            flag = funcion == null;
            if (flag) System.out.println("ID de función inválido.");
        } while (flag);
        return funcion;
    }

    private Sector seleccionarSector(Scanner sc, Funcion funcion) {
        System.out.println(funcion.asientosDisponibles());
        boolean flag;
        String entrada;
        Sector sector;
        do {
            System.out.print("Ingrese número del sector (o S para salir): ");
            entrada = sc.nextLine().trim();
            if (entrada.equalsIgnoreCase("s")) {
                System.out.println("Operación cancelada.");
                return null;
            }
            sector = null;
            for (Sector s : funcion.getRecinto().getSectores()) {
                if (String.valueOf(s.getId()).equals(entrada)) {
                    sector = s;
                    break;
                }
            }
            flag = sector == null;
            if (flag) System.out.println("Sector inválido.");
        } while (flag);
        return sector;
    }

    private Asiento reservarAsiento(Scanner sc, Sector sector) {
        if (!sector.isTieneAsientos()) {
            for (Asiento a : sector.getAsientos()) {
                if (a.isDisponible()) {
                    return a;
                }
            }
            System.out.println("No hay capacidad disponible en este sector.");
            return null;
        }

        boolean flag;
        String entrada;
        Asiento asiento;
        do {
            System.out.println(sector.verAsientosDisponibles());
            System.out.print("Ingrese número de asiento (o S para salir): ");
            entrada = sc.nextLine().trim();
            if (entrada.equalsIgnoreCase("s")) {
                System.out.println("Operación cancelada.");
                return null;
            }
            asiento = null;
            for (Asiento a : sector.getAsientos()) {
                if (String.valueOf(a.getId()).equals(entrada) && a.isDisponible()) {
                    asiento = a;
                    break;
                }
            }
            flag = asiento == null;
            if (flag) System.out.println("Asiento inválido o ya ocupado.");
        } while (flag);
        return asiento;
    }

    public void verMisTickets(Vendedor v, Boleteria boleteria) {
        System.out.println(LINEA);
        System.out.println("                  TUS TICKETS VENDIDOS");
        System.out.println(LINEA);

        if (v.getTicketsVendidos().isEmpty()) {
            System.out.println("          Aún no has vendido ningún ticket.");
            System.out.println(LINEA);
            return;
        }

        System.out.printf("Total vendidos: %d ticket%s%n%n", v.getTicketsVendidos().size(),
                v.getTicketsVendidos().size() == 1 ? "" : "s");

        for (Ticket t : v.getTicketsVendidos()) {
            Evento e = boleteria.getEventos().buscarElementoId(t.getEventoId());
            String nombre = e != null ? e.getNombre() : "[Evento eliminado]";
            Funcion f = e != null ? e.buscarFuncionPorId(t.getFuncionId()) : null;
            System.out.printf("ID %-4d → %-25s | %s | $%.2f%n",
                    t.getId(), nombre,
                    f != null ? UtilidadesGenerales.formatearFecha(LocalDateTime.parse(t.getFechaYHora())) : "???",
                    t.getPrecio());
        }
        System.out.println(LINEA);
    }

    public void calcularRecaudacion(Vendedor v) {
        System.out.println(LINEA);
        System.out.println("                     RECAUDACIÓN");
        System.out.println(LINEA);

        if (v.getTicketsVendidos().isEmpty()) {
            System.out.println("          No has vendido tickets aún.");
        } else {
            double total = 0;
            for (Ticket t : v.getTicketsVendidos()) total += t.getPrecio();
            System.out.printf("Total recaudado: $%.2f (%d tickets)%n", total, v.getTicketsVendidos().size());
        }
        System.out.println(LINEA);
    }

    public void reimprimirTicket(Scanner sc, Vendedor vendedor, Boleteria boleteria) {
        System.out.println(LINEA);
        System.out.println("                  REIMPRESIÓN DE TICKET");
        System.out.println(LINEA);

        System.out.print("ID del ticket a reimprimir (o S para salir): ");
        String entrada = sc.nextLine().trim();
        if (entrada.equalsIgnoreCase("s")) {
            System.out.println("Operación cancelada.");
            System.out.println(LINEA);
            return;
        }

        Ticket ticket = null;
        for (Ticket t : vendedor.getTicketsVendidos()) {
            if (String.valueOf(t.getId()).equals(entrada)) {
                ticket = t;
                break;
            }
        }

        if (ticket == null) {
            System.out.println("No se encontró un ticket con ese ID entre tus ventas.");
        } else {
            Evento e = boleteria.getEventos().buscarElementoId(ticket.getEventoId());
            Funcion f = e != null ? e.buscarFuncionPorId(ticket.getFuncionId()) : null;
            System.out.println(SEP);
            System.out.println("ID TICKET     : " + ticket.getId());
            System.out.println("EVENTO        : " + (e != null ? e.getNombre() : "[Eliminado]"));
            if (f != null) {
                System.out.println("FECHA         : " + UtilidadesGenerales.formatearFecha(f.getFechayHora()));
                System.out.println("RECINTO       : " + f.getRecinto().getNombre());
            }
            System.out.println("PRECIO        : $" + String.format("%.2f", ticket.getPrecio()));
            System.out.println("FECHA VENTA   : " + ticket.getFechaYHora().replace("T", " "));
            System.out.println(SEP);
            System.out.println("Ticket reimpreso correctamente.");
        }
        System.out.println(LINEA);
    }

    public void verMisEstadisticas(Vendedor vendedor) {
        System.out.println(LINEA);
        System.out.println("                   MIS ESTADÍSTICAS");
        System.out.println(LINEA);

        int totalTickets = vendedor.getTicketsVendidos().size();
        if (totalTickets == 0) {
            System.out.println("          Aún no has vendido tickets.");
        } else {
            double total = 0;
            for (Ticket t : vendedor.getTicketsVendidos()) total += t.getPrecio();
            System.out.printf("Tickets vendidos    : %d%n", totalTickets);
            System.out.printf("Recaudación total   : $%.2f%n", total);
            System.out.printf("Promedio por ticket : $%.2f%n", total / totalTickets);
        }
        System.out.println(LINEA);
    }

    public void crearVendedor(Scanner sc, Boleteria boleteria, String archivo) {
        System.out.println(LINEA);
        System.out.println("                  CREAR NUEVO VENDEDOR");
        System.out.println(LINEA);

        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine().trim();

        String email;
        boolean valido;
        do {
            System.out.print("Email: ");
            email = sc.nextLine().trim();
            valido = true;
            try {
                Validacion.validarEmail(email);
            } catch (EmailInvalidoException e) {
                System.out.println("Error: " + e.getMessage());
                valido = false;
                continue;
            }
            for (Usuario u : boleteria.getUsuarios().getElementos()) {
                if (u.getEmail().equalsIgnoreCase(email)) {
                    System.out.println("Este email ya está registrado.");
                    valido = false;
                    break;
                }
            }
        } while (!valido);

        String pass;
        do {
            System.out.print("Contraseña: ");
            pass = sc.nextLine();
            try {
                Validacion.validarContrasena(pass);
                break;
            } catch (ContraseniaInvalidaException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (true);

        Vendedor nuevo = new Vendedor(nombre, email, pass);
        boleteria.guardarUsuario(nuevo, archivo);

        System.out.println(SEP);
        System.out.println("VENDEDOR CREADO CORRECTAMENTE");
        System.out.println("ID asignado: " + nuevo.getId());
        System.out.println("Email: " + email);
        System.out.println(LINEA);
    }
}