/**
 * Clase de servicio que encapsula la lógica de negocio relacionada con las
 * operaciones que puede realizar un {@link Vendedor}, como la creación de tickets
 * y el registro de nuevos vendedores.
 */
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

    /**
     * Guía al {@link Vendedor} a través del proceso de venta de un nuevo {@link Ticket}.
     * El método maneja la interacción con el usuario (entrada por consola) para
     * seleccionar un Evento, una Funcion y un Asiento disponible.
     * Modifica el estado del asiento a no disponible y guarda los cambios en la boletería.
     *
     * @param sc        Objeto Scanner para la entrada de datos del usuario.
     * @param vendedor  El {@link Vendedor} actual que está realizando la venta.
     * @param boleteria El objeto {@link Boleteria} que gestiona los datos del sistema.
     * @param archivo   La ruta del archivo donde se deben guardar los datos de la boletería.
     */

    public void nuevoTicket(Scanner sc, Vendedor vendedor, Boleteria boleteria, String archivo) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Evento> eventos = boleteria.getEventos().getElementos();
        System.out.println(boleteria.mostrarEventos());

        boolean flag = false;
        String eventoId;
        Evento evento = null;

        // ================== SELECCIÓN DE EVENTO ==================
        do {
            System.out.println("Ingrese el id del evento o \"S\" para salir");
            eventoId = sc.nextLine().trim();

            if (eventoId.equalsIgnoreCase("s")) {
                System.out.println("...Saliendo...");
                flag = true;
            } else {
                for (Evento e : eventos) {
                    if (String.valueOf(e.getId()).equals(eventoId)) {
                        evento = e;
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    System.out.println("El numero ingresado es invalido, intentelo nuevamente");
                } else if (evento.getFuncionesDisponibles().isEmpty()) {
                    System.out.println("El evento seleccionado no tiene funciones disponibles");
                    flag = false;
                    evento = null;
                }
            }
        } while (!flag && !eventoId.equalsIgnoreCase("s"));
        if (eventoId.equalsIgnoreCase("s")) return;

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
            System.out.println("Ingrese el id de la funcion o \"S\" para salir");
            funcionId = sc.nextLine().trim();

            if (funcionId.equalsIgnoreCase("s")) {
                System.out.println("...Saliendo...");
                flag = true;
            } else {
                for (Funcion f : evento.getFunciones()) {
                    if (String.valueOf(f.getId()).equals(funcionId)) {
                        funcion = f;
                        flag = true;
                        break;
                    }
                }
                if (!flag) System.out.println("El numero ingresado es invalido, intentelo nuevamente");
            }
        } while (!flag && !funcionId.equalsIgnoreCase("s"));
        if (funcionId.equalsIgnoreCase("s")) return;

        // ================== SELECCIÓN DE SECTOR ==================
        System.out.println(funcion.asientosDisponibles());

        flag = false;
        Sector sector = null;
        String capturar;

        do {
            System.out.println("Ingrese el numero del sector o \"S\" para salir");
            capturar = sc.nextLine().trim();

            if (capturar.equalsIgnoreCase("s")) {
                System.out.println("...Saliendo...");
                flag = true;
            } else {
                for (Sector s : funcion.getRecinto().getSectores()) {
                    if (String.valueOf(s.getId()).equals(capturar)) {
                        sector = s;
                        flag = true;
                        break;
                    }
                }
                if (!flag) System.out.println("Debe ingresar un sector valido");
            }
        } while (!flag);
        if (capturar.equalsIgnoreCase("s")) return;

        // ================== OCUPAR ASIENTO (elegido o automático) ==================
        Asiento asiento = null;

        if (sector.isTieneAsientos()) {
            // Sector numerado → el usuario elige
            flag = false;
            do {
                System.out.println(sector.verAsientosDisponibles());
                System.out.println("Ingrese el numero del asiento o \"S\" para salir");
                capturar = sc.nextLine().trim();

                if (capturar.equalsIgnoreCase("s")) {
                    System.out.println("...Saliendo...");
                    flag = true;
                } else {
                    for (Asiento a : sector.getAsientos()) {
                        if (String.valueOf(a.getId()).equals(capturar) && a.isDisponible()) {
                            asiento = a;
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) System.out.println("El numero ingresado es invalido o ya está ocupado, intentelo nuevamente");
                }
            } while (!flag);
            if (capturar.equalsIgnoreCase("s")) return;

        } else {
            // Sector NO numerado → se ocupa automáticamente el primero disponible
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

        System.out.println("El ticket se generó correctamente");
        System.out.println("ID del ticket: " + ticket.getId());
    }

    /**
     * Permite registrar un nuevo {@link Vendedor} en el sistema, solicitando
     * nombre, email y contraseña con validación de formato y seguridad.
     *
     * @param sc        Objeto Scanner para la entrada de datos del usuario.
     * @param boleteria El objeto {@link Boleteria} donde se registrará el nuevo vendedor.
     * @param archivo   La ruta del archivo donde se deben guardar los datos de la boletería.
     */
    public void crearVendedor(Scanner sc, Boleteria boleteria, String archivo) {
        String nombre, email, contrasenia;
        boolean flagEmail = false;
        boolean flagContrasenia = false;
        System.out.println("Ingrese nombre de vendedor");
        nombre = sc.nextLine();
        do {
            System.out.println("Ingrese email");
            email = sc.nextLine();
            try {
                flagEmail = Validacion.validarEmail(email);

            } catch (EmailInvalidoException e) {
                System.out.println(e.getMessage());
            }
        } while (!flagEmail);
        do {
            System.out.println("ingrese contrasenia (Debe tener al menos 8 caracteres, una letra y un numero)");
            contrasenia = sc.nextLine();
            try {
                flagContrasenia = Validacion.validarContrasena(contrasenia);
            } catch (ContraseniaInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (!flagContrasenia);
        for (Usuario u : boleteria.getUsuarios().getElementos()) {
            if (email.equals(u.getEmail())) {
                flagEmail = false;
                System.out.println("El email ingresado ya esta en uso");
            }
        }
        if (flagEmail) {
            boleteria.guardarUsuario(new Vendedor(nombre, email, contrasenia), archivo);
            System.out.println("La cuenta se creo correctamente");
        }

    }

    /*
        public void modificarVendedor(Scanner sc, Boleteria boleteria, String archivo){
            int vendedorId;
            Vendedor vendedor;

            System.out.println("Vendedores:\n"+boleteria.mostrarVendedores());
            vendedorId=Validacion.validarEntero(sc,"Ingrese id del usuario a modificar");
            if (boleteria.getUsuarios().buscarElementoId(vendedorId) instanceof Vendedor){
                vendedor=(Vendedor) boleteria.getUsuarios().buscarElementoId(vendedorId);
            String nombre, email, contrasenia;
            boolean flagEmail = false;
            boolean flagContrasenia = false;
            System.out.println("Ingrese nombre de usuario");
            nombre = sc.nextLine();
            do {
                System.out.println("Ingrese email");
                email = sc.nextLine();
                try {
                    flagEmail = Validacion.validarEmail(email);

                } catch (EmailInvalidoException e) {
                    e.printStackTrace();
                }
            } while (!flagEmail);
            do {
                System.out.println("ingrese contrasenia");
                contrasenia = sc.nextLine();
                try {
                    flagContrasenia = Validacion.validarContrasena(contrasenia);
                } catch (ContraseniaInvalidaException e) {
                    e.printStackTrace();
                }

            } while (!flagContrasenia);
            vendedor.setNombre(nombre);
            vendedor.setContrasenia(contrasenia);
            vendedor.setEmail(email);
            boleteria.guardarBoleteria(archivo);
            }else System.out.println("El elemento seleccionado no es un vendedor");
        }
        */

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
            Funcion funcion = evento != null ? evento.buscarFuncionPorId(t.getFuncionId()) : null;
            Sector sector = funcion != null ? funcion.buscarSectorPorId(t.getSectorId()) : null;

            sb.append("ID Ticket: ").append(t.getId())
                    .append(" | Evento: ").append(t.getNombreEvento())
                    .append(" | Fecha: ").append(UtilidadesGenerales.formatearFecha(LocalDateTime.parse(t.getFechaYHora())))
                    .append(" | Precio: $").append(String.format("%.2f", t.getPrecio()));

            if (sector != null) {
                sb.append(" | Sector: ").append(sector.getNombre());
                if (sector.isTieneAsientos()) {
                    sb.append(" | Asiento: ").append(t.getAsiento());  // solo se muestra si es numerado
                }
            } else {
                sb.append(" | Sector: [No encontrado]");
            }
            sb.append("\n");
        }

        sb.append("////////////////////////////////////////////////////////////////////");
        System.out.println(sb.toString());
    }
    public void calcularRecaudacion(Vendedor v) {
        if (v == null || v.getTicketsVendidos().isEmpty()) {
            System.out.println("No hay datos de tickets vendidos.");

        } else {
            double recaudacion = 0;
            for (Ticket t : v.getTicketsVendidos()) {
                if (t != null) {
                    recaudacion += t.getPrecio();
                }
            }
            System.out.printf("Su recaudación total es de: $%.2f%n", recaudacion);
        }
    }
}
