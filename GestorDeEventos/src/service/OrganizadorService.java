package service;

import Utilidades.UtilidadesGenerales;
import Utilidades.Validacion;
import exceptions.*;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
                opcion = Integer.parseInt(sc.nextLine().trim());
                if (opcion < 1 || opcion > 5) {
                    System.out.println("Opción inválida. Debe ser del 1 al 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
                opcion = 0;
            }
        } while (opcion < 1 || opcion > 5);

        switch (opcion) {
            case 1 -> categoria = Categoria.CINE;
            case 2 -> categoria = Categoria.CONCIERTO;
            case 3 -> categoria = Categoria.TEATRO;
            case 4 -> categoria = Categoria.STAND_UP;
            case 5 -> categoria = Categoria.PARTIDO;
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

        // Mostrar solo eventos ACTIVOS
        System.out.println("/////////////////////// TUS EVENTOS ACTIVOS ///////////////////////");
        boolean hayActivos = false;
        for (Evento e : organizador.getEventosCreados()) {
            if (e.isActivo()) {
                hayActivos = true;
                System.out.printf("ID: %d | %s | %s | Funciones: %d%n",
                        e.getId(), e.getNombre(), e.getCategoria(), e.getFunciones().size());
            }
        }
        if (!hayActivos) {
            System.out.println("No tienes eventos activos a los que puedas agregar funciones.");
            return;
        }
        System.out.println("////////////////////////////////////////////////////////////////\n");

        Evento evento = null;
        boolean flag = false;

        do {
            System.out.println("Ingrese id del evento al que quiere agregar nuevas funciones:");
            String entrada = sc.nextLine().trim();

            try {
                int idEvento = Integer.parseInt(entrada);
                evento = organizador.buscarEvento(idEvento);

                if (evento == null) {
                    System.out.println("No tienes un evento con ese ID.");
                } else if (!evento.isActivo()) {
                    System.out.println("No puedes agregar funciones a un evento dado de baja.");
                    evento = null;
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
                asientos.add(new Asiento(j + 1));
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
        do {
            System.out.println("Ingrese contraseña (mínimo 8 caracteres, 1 letra y 1 número)");
            contrasenia = sc.nextLine();
            try {
                Validacion.validarContrasena(contrasenia);
                break;
            } catch (ContraseniaInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        boleteria.guardarUsuario(new Organizador(nombre, email, contrasenia), archivo);
        System.out.println("La cuenta se creó correctamente");
    }

    public void verMisEventos(Organizador o) {
        StringBuilder sb = new StringBuilder();
        sb.append("==================== MIS EVENTOS ====================\n");

        int activos = 0, inactivos = 0;

        for (Evento e : o.getEventosCreados()) {
            if (e.isActivo()) {
                activos++;
                sb.append("ACTIVO   | ");
            } else {
                inactivos++;
                sb.append("INACTIVO | ");
            }
            sb.append(String.format("ID: %d | %-25s | %-12s | Funciones: %d%n",
                    e.getId(), e.getNombre(), e.getCategoria(), e.getFunciones().size()));
        }

        sb.append("\n").append("─".repeat(60)).append("\n");
        sb.append(String.format("Total: %d | Activos: %d | Dados de baja: %d%n",
                o.getEventosCreados().size(), activos, inactivos));
        sb.append("==================================================\n");

        System.out.println(sb);
    }

    public void verMisFunciones(Scanner sc, Organizador o) {
        if (o.getEventosCreados().isEmpty()) {
            System.out.println("No tienes eventos creados.");
            return;
        }

        verMisEventos(o);

        boolean flag = false;
        Evento evento = null;

        do {
            System.out.println("Ingrese el id del evento o \"S\" para salir");
            String eventoId = sc.nextLine().trim();

            if (eventoId.equalsIgnoreCase("s")) {
                System.out.println("...Saliendo...");
                return;
            }

            for (Evento e : o.getEventosCreados()) {
                if (String.valueOf(e.getId()).equals(eventoId)) {
                    evento = e;
                    flag = true;
                    break;
                }
            }
            if (!flag) System.out.println("El número ingresado es inválido, inténtelo nuevamente");
        } while (!flag);

        StringBuilder sb = new StringBuilder();
        sb.append("================== FUNCIONES DEL EVENTO: ").append(evento.getNombre()).append(" ==================\n");
        sb.append("Estado del evento: ").append(evento.isActivo() ? "ACTIVO" : "DADO DE BAJA").append("\n\n");

        if (evento.getFunciones().isEmpty()) {
            sb.append("Este evento no tiene funciones creadas aún.\n");
        } else {
            for (Funcion f : evento.getFunciones()) {
                sb.append(String.format("ID: %d | %s | %s | Precio base: $%.2f | Disponibles: %d%n",
                        f.getId(),
                        UtilidadesGenerales.formatearFecha(f.getFechayHora()),
                        f.getRecinto().getNombre(),
                        f.getPrecioBase(),
                        f.cantidadAsientosDisponibles()));
            }
        }
        sb.append("==================================================================\n");
        System.out.println(sb);
    }

    public void darDeBajaEvento(Scanner sc, Organizador organizador, Boleteria boleteria, String archivo) {
        if (organizador.getEventosCreados().isEmpty()) {
            System.out.println("No tienes eventos creados para dar de baja.");
            return;
        }

        System.out.println("/////////////////////// TUS EVENTOS ACTIVOS ///////////////////////");
        boolean hayActivos = false;
        StringBuilder sb = new StringBuilder();

        for (Evento e : organizador.getEventosCreados()) {
            if (e.isActivo()) {
                hayActivos = true;
                sb.append(String.format("ID: %d | %-30s | %-12s | Funciones: %d%n",
                        e.getId(), e.getNombre(), e.getCategoria(), e.getFunciones().size()));
            }
        }

        if (!hayActivos) {
            System.out.println("No tienes eventos activos para dar de baja.");
            return;
        }
        System.out.println(sb);
        System.out.println("////////////////////////////////////////////////////////////////\n");

        boolean flag = false;
        Evento evento = null;
        String entrada;

        do {
            System.out.println("Ingrese el ID del evento a dar de baja (o \"S\" para cancelar):");
            entrada = sc.nextLine().trim();

            if (entrada.equalsIgnoreCase("s")) {
                System.out.println("Operación cancelada.");
                return;
            }

            for (Evento e : organizador.getEventosCreados()) {
                if (String.valueOf(e.getId()).equals(entrada) && e.isActivo()) {
                    evento = e;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                System.out.println("ID inválido o el evento ya está dado de baja. Intente nuevamente.");
            }
        } while (!flag);

        System.out.print("¿Está seguro de dar de baja el evento \"" + evento.getNombre() + "\"? (S/N): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.println("Operación cancelada.");
            return;
        }

        boolean tieneVentas = false;
        for (Funcion f : evento.getFunciones()) {
            if (f.cantidadAsientosDisponibles() < f.getRecinto().getCapacidad()) {
                tieneVentas = true;
                break;
            }
        }

        if (tieneVentas) {
            System.out.println("No se puede dar de baja: ya se vendieron tickets para este evento.");
        } else {
            evento.setActivo(false);
            boleteria.guardarBoleteria(archivo);
            System.out.println("Evento \"" + evento.getNombre() + "\" dado de baja correctamente.");
            System.out.println("Ya no aparecerá disponible para la venta de tickets.");
        }
    }
    public void reactivarEvento(Scanner sc, Organizador organizador, Boleteria boleteria, String archivo) {
        if (organizador.getEventosCreados().isEmpty()) {
            System.out.println("No tienes eventos creados.");
            return;
        }

        System.out.println("///////////////// TUS EVENTOS DADOS DE BAJA /////////////////");
        boolean hayInactivos = false;
        StringBuilder sb = new StringBuilder();

        for (Evento e : organizador.getEventosCreados()) {
            if (!e.isActivo()) {
                hayInactivos = true;
                sb.append(String.format("ID: %d | %s | %s | Funciones: %d%n",
                        e.getId(), e.getNombre(), e.getCategoria(), e.getFunciones().size()));
            }
        }

        if (!hayInactivos) {
            System.out.println("No tienes eventos dados de baja para reactivar.");
            return;
        }
        System.out.println(sb);
        System.out.println("///////////////////////////////////////////////////////////////\n");

        boolean flag = false;
        Evento evento = null;
        String entrada;

        do {
            System.out.println("Ingrese el ID del evento a reactivar (o \"S\" para cancelar):");
            entrada = sc.nextLine().trim();

            if (entrada.equalsIgnoreCase("s")) {
                System.out.println("Operación cancelada.");
                return;
            }

            for (Evento e : organizador.getEventosCreados()) {
                if (String.valueOf(e.getId()).equals(entrada) && !e.isActivo()) {
                    evento = e;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                System.out.println("ID inválido o el evento ya está activo.");
            }
        } while (!flag);

        System.out.print("¿Reactivar el evento \"" + evento.getNombre() + "\"? (S/N): ");
        if (sc.nextLine().trim().equalsIgnoreCase("s")) {
            evento.setActivo(true);
            boleteria.guardarBoleteria(archivo);
            System.out.println("¡Evento reactivado correctamente! Ahora está disponible para venta.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
}