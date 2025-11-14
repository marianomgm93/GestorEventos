package service;

import Utilidades.Validacion;
import exceptions.ContraseniaInvalidaException;
import exceptions.EmailInvalidoException;
import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class VendedorService {
    public void nuevoTicket(Scanner sc, Vendedor vendedor, ArrayList<Evento> eventos, Boleteria boleteria, String archivo) {
        StringBuilder sb = new StringBuilder();
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
        for (Funcion f : evento.getFunciones()) {
            sb.append("id: ").append(f.getId()).append("\t Fecha: ").append(f.getHora()).append("Recinto: ").append(f.getRecinto().getNombre()).append("\n");

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
            asientoId = Validacion.validarEntero(sc, "Ingrese el numero de asiento\n" + funcion.asientosDisponibles());

            for (Sector s : funcion.getRecinto().getSectores()) {
                for (Asiento a : s.getAsientos()) {
                    if (a.getNumero() == asientoId) {
                        asiento = a;
                        sector = s;
                        flag = true;
                    }
                }
            }
            if (!flag) System.out.println("El numero ingresado es invalido, intentelo nuevamente");

        } while (!flag);

        Ticket ticket = new Ticket(funcion.getRecinto().getDireccion(), asiento.getNumero(), evento.getNombre(), funcion.getHora(), sector.getTipo(), funcion.getPrecioBase());
        asiento.setDisponible(false);
        boleteria.guardarBoleteria(archivo);
        vendedor.getTicketsVendidos().add(ticket);
    }

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
        boleteria.guardarUsuario(new Vendedor(nombre, email, contrasenia), archivo);
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
