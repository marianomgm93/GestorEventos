/**
 * Clase de servicio que encapsula la lógica de negocio relacionada con las
 * operaciones que puede realizar un {@link Vendedor}, como la creación de tickets
 * y el registro de nuevos vendedores.
 */
package service;

import Utilidades.Validacion;
import exceptions.ContraseniaInvalidaException;
import exceptions.EmailInvalidoException;
import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class VendedorService {

    /**
     * Guía al {@link Vendedor} a través del proceso de venta de un nuevo {@link Ticket}.
     * El método maneja la interacción con el usuario (entrada por consola) para
     * seleccionar un Evento, una Funcion y un Asiento disponible.
     * Modifica el estado del asiento a no disponible y guarda los cambios en la boletería.
     *
     * @param sc Objeto Scanner para la entrada de datos del usuario.
     * @param vendedor El {@link Vendedor} actual que está realizando la venta.
     * @param boleteria El objeto {@link Boleteria} que gestiona los datos del sistema.
     * @param archivo La ruta del archivo donde se deben guardar los datos de la boletería.
     */
    public void nuevoTicket(Scanner sc, Vendedor vendedor, Boleteria boleteria, String archivo) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Evento> eventos=boleteria.getEventos().getElementos();
        System.out.println("Eventos:");
        for (Evento e : eventos) {
            sb.append("id: ").append(e.getId()).append("\t Nombre: ").append(e.getNombre()).append("\n");
        }
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
            if (!flag) System.out.println("El numero ingresado es invalido, intentelo nuevamente");
        } while (!flag);
        sb.setLength(0);

        flag = false;
        System.out.println("Funciones:");
        for (Funcion f : evento.getFunciones()) {
            sb.append("id: ").append(f.getId()).append("\tFecha: ").append(f.getFechayHora()).append("\tRecinto: ").append(f.getRecinto().getNombre()).append("\n");

        }
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
        int asientoId;
        do {
            asientoId = Validacion.validarEntero(sc, "Ingrese el id de asiento\n" + funcion.asientosDisponibles());

            for (Sector s : funcion.getRecinto().getSectores()) {
                for (Asiento a : s.getAsientos()) {
                    if (a.getId() == asientoId) {
                        asiento = a;
                        sector = s;
                        flag = true;
                    }
                }
            }
            if (!flag) System.out.println("El numero ingresado es invalido, intentelo nuevamente");

        } while (!flag);
        double modificador=0;
        switch (sector.getTipo()){
            case Tipo.PRINCIPAL:
                modificador=1.5;
                break;
            case Tipo.SECUNDARIO:
                modificador=1.25;
                break;
            case Tipo.TERCIARIO:
                modificador=1;
                break;
        }
        Ticket ticket = new Ticket(funcion.getRecinto().getDireccion(), asiento.getNumero(),evento.getId(), evento.getNombre(), funcion.getFechayHora().toString(), sector.getTipo(), funcion.getPrecioBase()*modificador);
        asiento.setDisponible(false);
        vendedor.getTicketsVendidos().add(ticket);
        boleteria.getVendidos().add(ticket);
        boleteria.guardarBoleteria(archivo);
    }

    /**
     * Permite registrar un nuevo {@link Vendedor} en el sistema, solicitando
     * nombre, email y contraseña con validación de formato y seguridad.
     *
     * @param sc Objeto Scanner para la entrada de datos del usuario.
     * @param boleteria El objeto {@link Boleteria} donde se registrará el nuevo vendedor.
     * @param archivo La ruta del archivo donde se deben guardar los datos de la boletería.
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
            System.out.println("ingrese contrasenia");
            contrasenia = sc.nextLine();
            try {
                flagContrasenia = Validacion.validarContrasena(contrasenia);
            } catch (ContraseniaInvalidaException e) {
                System.out.println(e.getMessage());
            }

        } while (!flagContrasenia);
        boleteria.guardarUsuario(new Vendedor(nombre, email, contrasenia), archivo);
        System.out.println("La cuenta se creo correctamente");

    }


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
}
