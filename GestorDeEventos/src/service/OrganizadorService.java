package service;

import Utilidades.UtilidadesGenerales;
import Utilidades.Validacion;
import exceptions.*;
import model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class OrganizadorService {

    private static final String LINEA = "═══════════════════════════════════════════════════════════════";
    private static final String SEP = "───────────────────────────────────────────────────────────────";

    private int calcularCapacidadTotal(ArrayList<Sector> sectores) {
        int total = 0;
        for (Sector sector : sectores) {
            total += sector.getAsientos().size();
        }
        return total;
    }

    public double validarDouble(Scanner sc, String mensaje, double minimo) {
        double valor;
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();
            try {
                valor = Double.parseDouble(entrada);
                if (valor < minimo) {
                    System.out.printf("Error: el valor debe ser mayor o igual a %.2f.%n", minimo);
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número válido (ej: 15000.50).");
            }
        }
    }

    public void nuevoEvento(Scanner sc, Organizador organizador, Boleteria boleteria, String archivo) {
        System.out.println(LINEA);
        System.out.println(" CREAR NUEVO EVENTO");
        System.out.println(LINEA);

        System.out.print("Nombre del evento: ");
        String nombre = sc.nextLine().trim();

        System.out.print("Descripción breve: ");
        String descripcion = sc.nextLine().trim();

        Categoria categoria = null;
        int opcion;
        do {
            System.out.println(SEP);
            System.out.println(" SELECCIONE CATEGORÍA");
            System.out.println(SEP);
            System.out.println("1: Cine | 2: Concierto | 3: Teatro");
            System.out.println("4: Stand Up | 5: Deportivo");
            System.out.println(SEP);
            opcion = Validacion.validarEntero(sc, "Opción (1-5): ", 1, 5);

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
        } while (categoria == null);

        Evento evento = new Evento(nombre, descripcion, categoria);

        try {
            boleteria.guardarEvento(evento, archivo);
            organizador.getEventosCreados().add(evento);
            boleteria.guardarBoleteria(archivo);
            System.out.println(SEP);
            System.out.println("EVENTO CREADO CON ÉXITO");
            System.out.printf("ID asignado: %d | %s%n", evento.getId(), evento.getNombre());
            System.out.println(LINEA);
        } catch (EventoRepetidoException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println(LINEA);
        }
    }

    public void agregarFuncion(Scanner sc, Organizador organizador, String archivo, Boleteria boleteria) {
        if (organizador.getEventosCreados().isEmpty()) {
            System.out.println("No tienes eventos creados aún.");
            return;
        }

        System.out.println(LINEA);
        System.out.println(" TUS EVENTOS ACTIVOS");
        System.out.println(LINEA);

        boolean hayActivos = false;
        for (Evento e : organizador.getEventosCreados()) {
            if (e.isActivo()) {
                hayActivos = true;
                System.out.printf("ID %-4d: %-30s | %-12s | Funciones: %d%n",
                        e.getId(), e.getNombre(), e.getCategoria(), e.getFunciones().size());
            }
        }

        if (!hayActivos) {
            System.out.println("No tienes eventos activos para agregar funciones.");
            System.out.println(LINEA);
            return;
        }

        System.out.println(LINEA);
        Evento evento = null;
        do {
            System.out.print("ID del evento para agregar función (o S para salir): ");
            String entrada = sc.nextLine().trim();
            if (entrada.equalsIgnoreCase("s")) {
                System.out.println("Operación cancelada.");
                return;
            }
            try {
                int id = Integer.parseInt(entrada);
                evento = organizador.buscarEvento(id);
                if (evento == null || !evento.isActivo()) {
                    System.out.println("Evento no encontrado o dado de baja. Intente nuevamente.");
                    evento = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido o 'S'.");
            }
        } while (evento == null);

        LocalDateTime fechaHora = Validacion.validarLocalDateTime(sc);
        double precioBase = validarDouble(sc, "Precio base de la función: $", 0);
        Recinto recinto = nuevoRecinto(sc);

        Funcion funcion = new Funcion(fechaHora, recinto, precioBase);

        if (Validacion.validarFuncion(boleteria, funcion)) {
            evento.getFunciones().add(funcion);
            boleteria.guardarBoleteria(archivo);
            System.out.println(SEP);
            System.out.println("FUNCIÓN AGREGADA CORRECTAMENTE");
            System.out.printf("Evento: %s | Fecha: %s%n", evento.getNombre(),
                    UtilidadesGenerales.formatearFecha(funcion.getFechayHora()));
            System.out.println(LINEA);
        } else {
            System.out.println("El recinto ya está ocupado en esa fecha y hora.");
            System.out.println(LINEA);
        }
    }

    public Recinto nuevoRecinto(Scanner sc) {
        System.out.println(SEP);
        System.out.println(" NUEVO RECINTO");
        System.out.println(SEP);

        System.out.print("Nombre del recinto: ");
        String nombre = sc.nextLine().trim();

        System.out.print("Dirección: ");
        String direccion = sc.nextLine().trim();

        ArrayList<Sector> sectores = generarSectores(sc);
        int capacidadTotal = calcularCapacidadTotal(sectores);

        System.out.println(SEP);
        System.out.println("RECINTO CREADO: " + nombre + " | Capacidad total: " + capacidadTotal);
        System.out.println(LINEA);

        return new Recinto(nombre, direccion, capacidadTotal, sectores);
    }

    public ArrayList<Sector> generarSectores(Scanner sc) {
        int cantidad = Validacion.validarEntero(sc, "Cantidad de sectores (1-6): ", 1, 6);
        ArrayList<Sector> sectores = new ArrayList<>();

        for (int i = 1; i <= cantidad; i++) {
            System.out.println(SEP);
            System.out.println(" SECTOR " + i + " DE " + cantidad);
            System.out.println(SEP);

            System.out.print("Nombre del sector: ");
            String nombre = sc.nextLine().trim();

            double extra = validarDouble(sc, "Valor agregado ($): $", 0);

            System.out.print("Tiene asientos numerados? (S/N): ");
            boolean numerados = sc.nextLine().trim().equalsIgnoreCase("s");

            int capacidad = Validacion.validarEntero(sc,
                    numerados ? "Cantidad de asientos numerados (1-200): " : "Capacidad del sector (1-200): ", 1, 200);

            ArrayList<Asiento> asientos = new ArrayList<>();
            for (int j = 1; j <= capacidad; j++) {
                asientos.add(new Asiento(j));
            }

            sectores.add(new Sector(nombre, extra, numerados, asientos));
        }
        return sectores;
    }

    public void crearOrganizador(Scanner sc, Boleteria boleteria, String archivo) {
        System.out.println(LINEA);
        System.out.println(" CREAR CUENTA DE ORGANIZADOR");
        System.out.println(LINEA);

        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine().trim();

        String email;
        boolean emailExiste;
        do {
            System.out.print("Email: ");
            email = sc.nextLine().trim();
            emailExiste = false;

            try {
                Validacion.validarEmail(email);
            } catch (EmailInvalidoException e) {
                System.out.println("Error: " + e.getMessage());
                continue;
            }

            for (int i = 0; i < boleteria.getUsuarios().getElementos().size(); i++) {
                Usuario usuario = boleteria.getUsuarios().getElementos().get(i);
                if (usuario.getEmail().equalsIgnoreCase(email)) {
                    emailExiste = true;
                    break;
                }
            }

            if (emailExiste) {
                System.out.println("Este email ya está registrado.");
            }
        } while (emailExiste || email.isEmpty());

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

        Organizador nuevo = new Organizador(nombre, email, pass);
        boleteria.guardarUsuario(nuevo, archivo);

        System.out.println(SEP);
        System.out.println("ORGANIZADOR CREADO CORRECTAMENTE");
        System.out.println("ID: " + nuevo.getId() + " | Email: " + email);
        System.out.println(LINEA);
    }

    public void verMisEventos(Organizador o) {
        System.out.println(LINEA);
        System.out.println(" MIS EVENTOS");
        System.out.println(LINEA);

        if (o.getEventosCreados().isEmpty()) {
            System.out.println(" Aún no has creado eventos.");
            System.out.println(LINEA);
            return;
        }

        int activos = 0, inactivos = 0;
        for (Evento e : o.getEventosCreados()) {
            String estado = e.isActivo() ? "ACTIVO   " : "INACTIVO";
            if (e.isActivo()) activos++; else inactivos++;
            System.out.printf("%s | ID %-4d: %-30s | %-12s | Funciones: %d%n",
                    estado, e.getId(), e.getNombre(), e.getCategoria(), e.getFunciones().size());
        }

        System.out.println(SEP);
        System.out.printf("Total: %d | Activos: %d | Dados de baja: %d%n",
                o.getEventosCreados().size(), activos, inactivos);
        System.out.println(LINEA);
    }

    public void verMisFunciones(Scanner sc, Organizador o) {
        if (o.getEventosCreados().isEmpty()) {
            System.out.println("No tienes eventos creados.");
            return;
        }

        verMisEventos(o);

        Evento evento = null;
        do {
            System.out.print("ID del evento para ver funciones (o S para salir): ");
            String entrada = sc.nextLine().trim();
            if (entrada.equalsIgnoreCase("s")) return;

            try {
                evento = o.buscarEvento(Integer.parseInt(entrada));
                if (evento == null) System.out.println("Evento no encontrado. Intente nuevamente.");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        } while (evento == null);

        System.out.println(LINEA);
        System.out.println(" FUNCIONES DE: " + evento.getNombre().toUpperCase());
        System.out.println("Estado: " + (evento.isActivo() ? "ACTIVO" : "DADO DE BAJA"));
        System.out.println(LINEA);

        if (evento.getFunciones().isEmpty()) {
            System.out.println("Este evento no tiene funciones creadas aún.");
        } else {
            for (Funcion f : evento.getFunciones()) {
                System.out.printf("ID %-3d: %s | %-20s | Precio base: $%.2f | Disponibles: %d%n",
                        f.getId(),
                        UtilidadesGenerales.formatearFecha(f.getFechayHora()),
                        f.getRecinto().getNombre(),
                        f.getPrecioBase(),
                        f.cantidadAsientosDisponibles());
            }
        }
        System.out.println(LINEA);
    }
    public void darDeBajaEvento(Scanner sc, Organizador organizador, Boleteria boleteria, String archivo) {
        if (organizador.getEventosCreados().isEmpty()) {
            System.out.println("No tienes eventos creados.");
            return;
        }

        System.out.println(LINEA);
        System.out.println(" EVENTOS ACTIVOS (para dar de baja)");
        System.out.println(LINEA);

        boolean hayActivos = false;
        for (Evento e : organizador.getEventosCreados()) {
            if (e.isActivo()) {
                hayActivos = true;
                System.out.printf("ID %-4d: %-30s | %-12s | Funciones: %d%n",
                        e.getId(), e.getNombre(), e.getCategoria(), e.getFunciones().size());
            }
        }

        if (!hayActivos) {
            System.out.println("No tienes eventos activos para dar de baja.");
            System.out.println(LINEA);
            return;
        }

        System.out.println(LINEA);
        Evento evento = null;
        do {
            System.out.print("ID del evento a dar de baja (o S para cancelar): ");
            String entrada = sc.nextLine().trim();
            if (entrada.equalsIgnoreCase("s")) {
                System.out.println("Operación cancelada.");
                return;
            }
            try {
                int id = Integer.parseInt(entrada);
                evento = organizador.buscarEvento(id);
                if (evento == null || !evento.isActivo()) {
                    System.out.println("ID inválido o ya dado de baja.");
                    evento = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        } while (evento == null);

        System.out.print("Confirmar baja del evento \"" + evento.getNombre() + "\"? (S/N): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.println("Operación cancelada.");
            System.out.println(LINEA);
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
            System.out.println("No se puede dar de baja: ya se vendieron tickets.");
        } else {
            evento.setActivo(false);
            boleteria.guardarBoleteria(archivo);
            System.out.println("Evento \"" + evento.getNombre() + "\" dado de baja correctamente.");
        }
        System.out.println(LINEA);
}

    public void reactivarEvento(Scanner sc, Organizador organizador, Boleteria boleteria, String archivo) {
        if (organizador.getEventosCreados().isEmpty()) {
            System.out.println("No tienes eventos creados.");
            return;
        }

        System.out.println(LINEA);
        System.out.println(" EVENTOS DADOS DE BAJA (para reactivar)");
        System.out.println(LINEA);

        boolean hayInactivos = false;
        for (Evento e : organizador.getEventosCreados()) {
            if (!e.isActivo()) {
                hayInactivos = true;
                System.out.printf("ID %-4d: %-30s | %-12s | Funciones: %d%n",
                        e.getId(), e.getNombre(), e.getCategoria(), e.getFunciones().size());
            }
        }

        if (!hayInactivos) {
            System.out.println("No tienes eventos dados de baja.");
            System.out.println(LINEA);
            return;
        }

        System.out.println(LINEA);
        Evento evento = null;
        do {
            System.out.print("ID del evento a reactivar (o S para cancelar): ");
            String entrada = sc.nextLine().trim();
            if (entrada.equalsIgnoreCase("s")) {
                System.out.println("Operación cancelada.");
                return;
            }
            try {
                evento = organizador.buscarEvento(Integer.parseInt(entrada));
                if (evento == null || evento.isActivo()) {
                    System.out.println("ID inválido o ya está activo.");
                    evento = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        } while (evento == null);

        System.out.print("Reactivar el evento \"" + evento.getNombre() + "\"? (S/N): ");
        if (sc.nextLine().trim().equalsIgnoreCase("s")) {
            evento.setActivo(true);
            boleteria.guardarBoleteria(archivo);
            System.out.println("Evento \"" + evento.getNombre() + "\" reactivado correctamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
        System.out.println(LINEA);
    }
}