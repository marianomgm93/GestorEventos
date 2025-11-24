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
        String capturar;
        ArrayList<Evento> eventos = boleteria.getEventos().getElementos();
        sb.append("////////////////////////////////////////////////");
        sb.append("Eventos:");
        for (Evento e : eventos) {
            sb.append("id: ").append(e.getId()).append("\t Nombre: ").append(e.getNombre()).append("\n");
        }
        sb.append("////////////////////////////////////////////////");
        boolean flag = false;
        int eventoId;
        Evento evento = null;
        do {
            System.out.println(sb);
            eventoId = Validacion.validarEntero(sc, "Ingrese id del evento");
            for (Evento e : eventos) {
                if (e.getId() == eventoId) {
                    evento = e;
                    flag = true;
                }
            }
            if (!flag) System.out.println("El numero ingresado es invalido , intentelo nuevamente");
            else if (evento!=null && evento.getFunciones().isEmpty()) {
                flag=false;
                System.out.println("El evento seleccionado no tiene ninguna funcion disponible");
            }
        } while (!flag);
        sb.setLength(0);

        flag = false;
        sb.append("\n////////////////////////////////////////////////");
        sb.append("\nFunciones:");
        for (Funcion f : evento.getFunciones()) {
            sb.append("\nid: ").append(f.getId()).append("\tFecha: ").append(UtilidadesGenerales.formatearFecha(f.getFechayHora())).append("\tRecinto: ").append(f.getRecinto().getNombre()).append("\n");
        }
        sb.append("\n////////////////////////////////////////////////");

        int funcionId;
        Funcion funcion = null;
        do {
            System.out.println(sb);
            funcionId = Validacion.validarEntero(sc, "Ingrese id de la funcion");
            for (Funcion f : evento.getFunciones()) {
                if (f.getId() == funcionId) {
                    funcion = f;
                    flag = true;
                }
            }
            if (!flag) System.out.println("El numero ingresado es invalido, intentelo nuevamente");
        } while (!flag);
        flag = false;
        Asiento asiento = null;
        Sector sector = null;
        System.out.println("////////////////////////////////////////////////");
        System.out.println(funcion.asientosDisponibles());
        System.out.println("////////////////////////////////////////////////");
        do {

            System.out.println("Ingrese el numero del sector o \"S\" para salir");
            capturar = sc.nextLine();
            if (!capturar.equalsIgnoreCase("s")) {
                for (Sector s : funcion.getRecinto().getSectores()) {
                    if (("" + s.getId()).equals(capturar)) {
                        sector = s;
                        flag = true;
                    }
                }

            } else flag = true;
            if (!flag) System.out.println("Debe ingresar un sector valido");
        } while (!flag);
        flag = false;
        if (sector != null && sector.isTieneAsientos()) {

            do {

                System.out.println(sector.verAsientosDisponibles());
                System.out.println("Ingrese el numero del asiento o \"S\" para salir");
                capturar = sc.nextLine();
                if (!capturar.equalsIgnoreCase("s")) {
                    for (Asiento a : sector.getAsientos()) {
                        if (("" + a.getId()).equals(capturar) && a.isDisponible()) {
                            asiento = a;
                            flag = true;
                        }
                    }

                    if (!flag) System.out.println("El numero ingresado es invalido, intentelo nuevamente");
                } else {
                    flag = true;
                    System.out.println("...Saliendo...");
                }

            } while (!flag);
            if (asiento != null) {
                Ticket ticket = new Ticket(funcion.getRecinto().getDireccion(), asiento.getNumero(), evento.getId(), funcionId, sector.getId(), evento.getNombre(), funcion.getFechayHora().toString(), funcion.getPrecioBase() + sector.getValorExtra());
                asiento.setDisponible(false);
                vendedor.getTicketsVendidos().add(ticket);
                boleteria.getVendidos().add(ticket);
                boleteria.guardarBoleteria(archivo);
                System.out.println("El ticket se generó correctamente");

            }
        } else {
            System.out.println("Volviendo al menu anterior...");
        }
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
        StringBuilder sb = new StringBuilder();

        System.out.println("///////////////////////Inicio tickets vendidos///////////////////////");
        sb.append("Total de tickets vendidos: ").append(v.getTicketsVendidos().size());
        for (Ticket t : v.getTicketsVendidos()) {
            sb.append("\nId:").append(t.getId()).append("\tEvento: ").append(t.getNombreEvento())
                    .append("\tFecha funcion: ").append(UtilidadesGenerales.formatearFecha(LocalDateTime.parse(t.getFechaYHora())))
                    .append("\tPrecio: ").append(t.getPrecio())
                    .append("\tSector: ").append(boleteria.getEventos().buscarElementoId(t.getSectorId()).buscarFuncionPorId(t.getFuncionId()).buscarSectorPorId(t.getSectorId()).getNombre());
            if (boleteria.getEventos().buscarElementoId(t.getEventoId()).buscarFuncionPorId(t.getFuncionId()).buscarSectorPorId(t.getSectorId()).isTieneAsientos()) {
                sb.append("\tAsiento: ").append(t.getAsiento());
            }
        }
        System.out.println(sb);
        System.out.println("///////////////////////Fin tickets vendidos///////////////////////");
    }

    public void calcularRecaudacion(Vendedor v) {
        if (v == null || v.getTicketsVendidos() == null) {
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
