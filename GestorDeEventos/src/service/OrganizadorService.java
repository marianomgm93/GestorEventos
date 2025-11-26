package service;

import Utilidades.UtilidadesGenerales;
import Utilidades.Validacion;
import exceptions.*;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OrganizadorService {

    public void nuevoEvento(Scanner sc, Organizador organizador, Boleteria boleteria, String archivo) {
        System.out.println("Ingrese nombre del evento");
        String nombre = sc.nextLine().trim();

        System.out.println("Ingrese una descripcion breve");
        String descripcion = sc.nextLine().trim();

        int opcion = 0;
        Categoria categoria = null;

        do {
            System.out.println("Categorias:\n1\tCine\n2\tConcierto\n3\tTeatro\n4\tStand UP\n5\tDeportivo");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());  // Evita el problema del nextInt()
                if (opcion < 1 || opcion > 5) {
                    System.out.println("Opción inválida. Debe ser del 1 al 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
                opcion = 0;
            }
        } while (opcion < 1 || opcion > 5);

        switch (opcion) {
            case 1:
                categoria = Categoria.CINE;
                break;
            case 2:
                categoria = Categoria.CONCIERTO;
                break;
            case 3:
                categoria = Categoria.TEATRO;
                break;
            case 4:
                categoria = Categoria.STAND_UP;
                break;
            case 5:
                categoria = Categoria.PARTIDO;
                break;
        }

        Evento evento = new Evento(nombre, descripcion, categoria);
        try {
            boleteria.guardarEvento(evento, archivo);
            organizador.getEventosCreados().add(evento);
            boleteria.guardarBoleteria(archivo);
            System.out.println("El evento se creó correctamente");
        } catch (EventoRepetidoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void agregarFuncion(Scanner sc, Organizador organizador, String archivo, Boleteria boleteria) {
        if (organizador.getEventosCreados().isEmpty()) {
            System.out.println("Debes tener eventos creados para agregar nuevas funciones");
            return;
        }

        verMisEventos(organizador);

        Evento evento = null;
        boolean flag = false;
        do {
            System.out.println("Ingrese id del evento al que quiere agregar nuevas funciones:");
            String entrada = sc.nextLine().trim();
            try {
                int idEvento = Integer.parseInt(entrada);
                evento = organizador.buscarEvento(idEvento);
                if (evento == null) {
                    System.out.println("No tenés un evento con ese ID.");
                } else {
                    flag = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            } catch (ElementoNoEncontradoException e) {
                System.out.println(e.getMessage());
            }
        } while (!flag);

        LocalDateTime fechayHora = Validacion.validarLocalDateTime(sc);

        double precio = 0;
        flag = false;
        do {
            System.out.println("Ingrese el precio base");
            try {
                precio = Double.parseDouble(sc.nextLine().trim());
                if (precio < 0) {
                    System.out.println("El precio no puede ser negativo.");
                } else {
                    flag = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        } while (!flag);

        Recinto recinto = nuevoRecinto(sc);
        Funcion funcion = new Funcion(fechayHora, recinto, precio);

        if (Validacion.validarFuncion(boleteria, funcion)) {
            evento.getFunciones().add(funcion);
            System.out.println("La función se creó correctamente");
            boleteria.guardarBoleteria(archivo);
        } else {
            System.out.println("El recinto seleccionado se encuentra ocupado en esa fecha y hora");
        }
    }

    public Recinto nuevoRecinto(Scanner sc) {
        System.out.println("Ingrese nombre del recinto");
        String nombre = sc.nextLine().trim();

        System.out.println("Ingrese direccion del recinto");
        String direccion = sc.nextLine().trim();

        ArrayList<Sector> sectores = generarSectores(sc);

        int capacidad = 0;
        for (Sector s : sectores) {
            capacidad += s.getAsientos().size();
        }

        return new Recinto(nombre, direccion, capacidad, sectores);
    }

    public ArrayList<Sector> generarSectores(Scanner sc) {
        int cantidadSectores = Validacion.validarEntero(sc, "Ingrese la cantidad de sectores que desea agregar (1 - 6): ", 1, 6);
        ArrayList<Sector> sectores = new ArrayList<>();

        for (int i = 0; i < cantidadSectores; i++) {
            System.out.println("Ingrese nombre del sector " + (i + 1) + ":");
            String nombre = sc.nextLine().trim();

            double extra = Validacion.validarPrecio(sc, "Ingrese un valor agregado para este sector");

            System.out.println("¿El sector tiene asientos numerados? (S/N)");
            String opcion = sc.nextLine().trim();
            boolean tieneAsientos = opcion.equalsIgnoreCase("s");

            String mensaje = tieneAsientos ? "cantidad de asientos" : "capacidad";
            int cantidadAsientos = Validacion.validarEntero(sc,
                    "Ingrese la " + mensaje + " del sector " + (i + 1) + " (1-200): ", 1, 200);

            ArrayList<Asiento> asientos = new ArrayList<>();
            for (int j = 0; j < cantidadAsientos; j++) {
                asientos.add(new Asiento(j + 1)); // numeración desde 1
            }

            sectores.add(new Sector(nombre, extra, tieneAsientos, asientos));
        }
        return sectores;
    }

    public void crearOrganizador(Scanner sc, Boleteria boleteria, String archivo) {
        System.out.println("Ingrese nombre de organizador");
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
        boolean contraseniaValida;
        do {
            System.out.println("Ingrese contraseña (mínimo 8 caracteres, 1 letra y 1 número)");
            contrasenia = sc.nextLine();
            try {
                Validacion.validarContrasena(contrasenia);
                contraseniaValida = true;
            } catch (ContraseniaInvalidaException e) {
                System.out.println(e.getMessage());
                contraseniaValida = false;
            }
        } while (!contraseniaValida);

        boleteria.guardarUsuario(new Organizador(nombre, email, contrasenia), archivo);
        System.out.println("La cuenta se creó correctamente");
    }

    public void verMisEventos(Organizador o) {
        StringBuilder sb = new StringBuilder();
        sb.append("/////////////////////// Eventos Creados ///////////////////////\n");
        sb.append("Total de eventos: ").append(o.getEventosCreados().size()).append("\n\n");

        if (o.getEventosCreados().isEmpty()) {
            sb.append("No has creado ningún evento aún.\n");
        } else {
            for (Evento e : o.getEventosCreados()) {
                sb.append("ID: ").append(e.getId())
                        .append(" | Nombre: ").append(e.getNombre())
                        .append(" | Categoría: ").append(e.getCategoria())
                        .append(" | Funciones: ").append(e.getFunciones().size())
                        .append("\n");
            }
        }
        sb.append("///////////////////////////////////////////////////////////////");
        System.out.println(sb);
    }

    public void verMisFunciones(Scanner sc, Organizador o) {
        if (o.getEventosCreados().isEmpty()) {
            System.out.println("No tienes eventos creados.");
            return;
        }

        verMisEventos(o);

        boolean flag = false;
        String eventoId;
        Evento evento = null;

        do {
            System.out.println("Ingrese el id del evento o \"S\" para salir");
            eventoId = sc.nextLine().trim();

            if (eventoId.equalsIgnoreCase("s")) {
                System.out.println("...Saliendo...");
                return;
            }

            flag = false;
            for (Evento e : o.getEventosCreados()) {
                if (String.valueOf(e.getId()).equals(eventoId)) {
                    evento = e;
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                System.out.println("El número ingresado es inválido, inténtelo nuevamente");
            }
        } while (!flag);

        StringBuilder sb = new StringBuilder();
        sb.append("/////////////////////// FUNCIONES DEL EVENTO ///////////////////////\n");
        sb.append("Evento: ").append(evento.getNombre()).append("\n\n");

        if (evento.getFunciones().isEmpty()) {
            sb.append("Este evento no tiene funciones creadas aún.\n");
        } else {
            for (Funcion f : evento.getFunciones()) {
                sb.append("ID: ").append(f.getId())
                        .append(" | Fecha: ").append(UtilidadesGenerales.formatearFecha(f.getFechayHora()))
                        .append(" | Recinto: ").append(f.getRecinto().getNombre())
                        .append(" | Precio base: $").append(String.format("%.2f", f.getPrecioBase()))
                        .append(" | Disponibles: ").append(f.cantidadAsientosDisponibles())
                        .append("\n");
            }
        }
        sb.append("////////////////////////////////////////////////////////////////////");
        System.out.println(sb);
    }
}