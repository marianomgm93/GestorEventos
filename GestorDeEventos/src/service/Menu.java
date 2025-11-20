package service;

import Utilidades.Validacion;
import exceptions.UsuarioInvalidoException;
import model.*;

import java.util.Scanner;

public class Menu {
    public void inicio(Scanner sc, Boleteria boleteria, String archivo) {
        boolean flag = false;
        int option;
        do {
            System.out.println("============ Bienvenido ============");
            System.out.println("1\tRegistrar un nuevo usuario\n2\tIniciar sesion\n");
            option = Validacion.validarEntero(sc);
            switch (option) {
                case 1:
                    menuNuevoUsuario(sc, boleteria, archivo);
                    break;
                case 2:
                    loginMenu(sc, boleteria, archivo);
                    break;
                //case 3:
                //    break;
                default:
                    System.out.println("El numero ingresado es invalido");
                    break;
            }

        } while (!flag);


    }

    public void menuNuevoUsuario(Scanner sc, Boleteria boleteria, String archivo) {
        boolean flag = false;
        int option;
        OrganizadorService organizadorService = new OrganizadorService();
        VendedorService vendedorService = new VendedorService();
        do {
            System.out.println("============ Creacion Usuario ============");
            System.out.println("0\tAtras\n1\tCrear cuenta de organizador\n2\tCrear cuenta de vendedor");
            option = Validacion.validarEntero(sc);
            switch (option) {
                case 0:
                    System.out.println("...Saliendo...");
                    flag = true;
                    break;
                case 1:
                    organizadorService.crearOrganizador(sc, boleteria, archivo);
                    break;
                case 2:
                    vendedorService.crearVendedor(sc, boleteria, archivo);
                    break;
                default:
                    System.out.println("La opcion ingresada es incorrecta");
                    break;
            }

        } while (!flag);

    }

    public void loginMenu(Scanner sc, Boleteria boleteria, String archivo) {
        boolean flag = false;
        Usuario usuario = null;

        System.out.println("============ Iniciar Sesion ============");
        System.out.println("ingrese email:");
        String email = sc.nextLine();
        System.out.println("ingrese contrasenia:");
        String contrasenia = sc.nextLine();
        try {
            usuario = boleteria.buscarPorEmail(email);
            flag = Validacion.validarUsuario(usuario, contrasenia);

        } catch (UsuarioInvalidoException e) {
            System.out.println(e.getMessage());

        }
        if(flag){
        if (usuario instanceof Vendedor) {
            menuVendedor(sc, boleteria, (Vendedor) usuario, archivo);
        } else if (usuario instanceof Organizador) {
            menuOrganizador(sc, boleteria, (Organizador) usuario, archivo);
        } else if (usuario instanceof Administrador) {
            menuAdministrador(boleteria, sc, (Administrador) usuario, archivo);
        }

        }
    }

    public void menuVendedor(Scanner sc, Boleteria b, Vendedor vendedor, String archivo) {
        VendedorService vs = new VendedorService();
        boolean flag = true;
        String option;
        do {
            System.out.println("============ Panel vendedor ============");
            option = "";
            System.out.println("0\tSalir\n1\tVer eventos\n2\tNuevo ticket\n3\tVer mis tickets vendidos\n" +
                    "4\tCalcular mi recaudacion\n");
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
                    vs.nuevoTicket(sc, vendedor, b, archivo);
                    break;
                case "3":
                    vs.verMisTickets(vendedor);
                    break;
                case "4":
                    vs.calcularRecaudacion(vendedor);
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
            System.out.println("============ Panel organizador ============");
            option = "";
            System.out.println("0\tSalir\n1\tVer eventos\n2\tNuevo Evento\n3\tAgregar funcion\n4\tVer mis eventos\n" +
                    "5\tVer mis funciones");
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
                    os.agregarFuncion(sc, organizador, archivo, b);
                    break;
                case "4":
                    os.verMisEventos(organizador);
                    break;
                case "5":
                    os.verMisFunciones(sc,organizador);
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        } while (flag);
    }

    public void menuAdministrador(Boleteria boleteria, Scanner sc, Administrador admin,String archivo) {
        boolean flag=false;
        AdministradorService as= new AdministradorService();
        do {
            System.out.println("============ Panel Administracion ============");

            System.out.println("Seleccione una de las siguientes opciones:\n" +
                    "0\tSalir\n" +
                    "1\tVer todos los usuarios\n" +
                    "2\tEliminar usuario\n" +
                    "3\tVer usuarios activos\n" +
                    "4\tVer usuarios inactivos\n");
            int option = Validacion.validarEntero(sc);
            switch (option){
                case 0:
                    flag=true;
                    break;
                case 1:
                    System.out.println(as.verUsuarios(boleteria));
                    break;
                case 2:
                    as.eliminarUsuario(sc,boleteria,archivo);
                    break;
                case 3:
                    as.verUsuariosActivos(boleteria);
                    break;
                case 4:
                    as.verUsuariosInactivos(boleteria);
                    break;
                default:
                    System.out.println("El numero ingresado es invalido");
            }
        }while(!flag);

    }
}
