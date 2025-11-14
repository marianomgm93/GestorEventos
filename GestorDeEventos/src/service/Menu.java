package service;

import Utilidades.Validacion;
import exceptions.UsuarioInvalidoException;
import model.Boleteria;
import model.Organizador;
import model.Usuario;
import model.Vendedor;

import java.util.Scanner;

public class Menu {
    public void lanzarMenu(Scanner sc, Boleteria boleteria, String archivo) {
        boolean flag = false;
        Usuario usuario = null;
        do {

            System.out.println("ingrese email");
            String email = sc.nextLine();
            System.out.println("ingrese contrasenia");
            String contrasenia = sc.nextLine();
            try {
                usuario = boleteria.buscarPorEmail(email);
                flag = Validacion.validarUsuario(usuario, contrasenia);
            } catch (UsuarioInvalidoException e) {
                e.printStackTrace();
            }
        } while (!flag);
        if (usuario instanceof Vendedor) {
            menuVendedor(sc, boleteria, (Vendedor) usuario, archivo);
        } else if (usuario instanceof Organizador) {
            menuOrganizador(sc, boleteria, (Organizador) usuario, archivo);
        }
    }

    public void menuVendedor(Scanner sc, Boleteria b, Vendedor vendedor, String archivo) {
        VendedorService vs = new VendedorService();
        boolean flag = true;
        String option;
        do {
            System.out.println("Menu vendedor:");
            option = "";
            System.out.println("0\tSalir\n1\tVer eventos\n2\tNuevo ticket");
            option = sc.nextLine();

            switch (option) {
                case "0":
                    flag = false;
                    break;
                case "1":
                    System.out.println("Eventos:");
                    System.out.println(b.mostrarEventos());
                    break;
                case "2":
                    vs.nuevoTicket(sc, vendedor, b.getEventos().getElementos());
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        } while (flag);
    }

    public void menuOrganizador(Scanner sc, Boleteria b, Organizador organizador, String archivo) {
        OrganizadorService os = new OrganizadorService();
        boolean flag = true;
        String option;
        do {
            System.out.println("Menu organizador:");
            option = "";
            System.out.println("0\tSalir\n1\tVer eventos\n2\tNuevo Evento\n3\tAgregar funcion");
            option = sc.nextLine();

            switch (option) {
                case "0":
                    flag = false;
                    break;
                case "1":
                    System.out.println("Eventos:");
                    System.out.println(b.mostrarEventos());
                    break;
                case "2":
                    os.nuevoEvento(sc, organizador, b, archivo);
                    break;
                case "3":
                    os.agregarFuncion(sc, b, archivo);
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        } while (flag);
    }
}
