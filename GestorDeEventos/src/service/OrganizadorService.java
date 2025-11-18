package service;

import Utilidades.Validacion;
import exceptions.*;
import model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OrganizadorService {
    public void nuevoEvento(Scanner sc, Organizador organizador, Boleteria boleteria, String archivo) {

        System.out.println("Ingrese nombre del evento");
        String nombre = sc.nextLine();
        System.out.println("Ingrese una descripcion breve");
        String descripcion = sc.nextLine();
        int opcion = 0;
        Categoria categoria = Categoria.CINE;
        do {
            System.out.println("Categorias:\n1\tCine\n2\tConcierto\n3\tTeatro\n4\tStand UP\n5\tDeportivo");
            try {
                opcion = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un numero contemplado entre las opciones");
                sc.nextLine();
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
        } catch (UsuarioRepetidoException e) {
            e.printStackTrace();
        } catch (EventoRepetidoException e) {
            e.printStackTrace();
        }
    }

    public void modificarEvento(Scanner sc, Boleteria boleteria, String archivo) {
        boolean flag = false;
        int eventoId;
        Evento evento;
        do {
            System.out.println("Ingrese id del evento");
            eventoId = Validacion.validarEntero(sc);
            evento = boleteria.getEventos().buscarElementoId(eventoId);
            if (evento != null) {
                flag = true;
            }
        } while (!flag);

        System.out.println("Ingrese nombre del evento");
        String nombre = sc.nextLine();
        System.out.println("Ingrese una descripcion breve");
        String descripcion = sc.nextLine();
        int opcion = 0;
        Categoria categoria = Categoria.CINE;
        do {
            System.out.println("Categorias:\n1\tCine\n2\tConcierto\n3\tTeatro\n4\tStand UP\n5\tDeportivo");

            opcion = Validacion.validarEntero(sc);

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

        evento.setNombre(nombre);
        evento.setCategoria(categoria);
        evento.setDescripcion(descripcion);
        boleteria.guardarBoleteria(archivo);
    }

    public void agregarFuncion(Scanner sc, Organizador organizador, String archivo,Boleteria boleteria) {
        LocalDateTime fechayHora;
        double precio = 0;
        boolean flag = false;
        verMisEventos(organizador);
        Evento evento=null;
        do{
            System.out.println("Ingrese id del evento al que quiere agregar nuevas funciones:");
            int idEvento = Validacion.validarEntero(sc);
            try{
                evento=organizador.buscarEvento(idEvento);
            }catch(ElementoNoEncontradoException e){
                System.out.println(e.getMessage());
            }


        }while(evento==null);
        fechayHora=Validacion.validarLocalDateTime(sc);

        System.out.println("Ingrese el precio base");
        do {
            try {
                precio = sc.nextDouble();
                sc.nextLine();
                flag = true;
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un numero");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("El numero ingresado es invalido");
                sc.nextLine();
            }
        } while (!flag);
        Recinto recinto = nuevoRecinto(sc);
        Funcion funcion = new Funcion(fechayHora, recinto, precio);
        evento.getFunciones().add(funcion);
        boleteria.guardarBoleteria(archivo);
    }

    public Recinto nuevoRecinto(Scanner sc) {
        System.out.println("Ingrese nombre del recinto");
        String nombre = sc.nextLine();
        System.out.println("Ingrese direccion del recinto");
        String direccion = sc.nextLine();
        int capacidad = 0;
        ArrayList<Sector> sectores = generarSectores(sc);
        for (int i = 0; i < sectores.size(); i++) {
            capacidad += sectores.get(i).getAsientos().size();
        }
        return new Recinto(nombre, direccion, capacidad, sectores);
    }

    public ArrayList<Sector> generarSectores(Scanner sc) {
        int cantidadSectores = 0;
        boolean flag = false;
        do {
            System.out.println("Ingrese la cantidad de sectores que desea agregar(entre 1 y 6)");
            try {
                cantidadSectores = sc.nextInt();
                sc.nextLine();
                if (cantidadSectores > 0 && cantidadSectores < 7) {
                    flag = true;
                } else throw new NumeroInvalidoException("El valor debe estar entre 1 y 6");
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un numero entero");
                sc.nextLine();
            } catch (NumeroInvalidoException e) {
                System.out.println(e.getMessage());
            }
        } while (!flag);
        ArrayList<Sector> sectores = new ArrayList<>();
        String nombre;
        int option;
        int cantidadAsientos = 0;
        Tipo tipo = Tipo.TERCIARIO;
        for (int i = 0; i < cantidadSectores; i++) {
            System.out.println("Ingrese nombre del sector: " + (i + 1));
            nombre = sc.nextLine();
            flag = false;
            do {
                System.out.println("Ingrese tipo del sector:\n1\tPrimario\n2\tSecundario\n3\tTerciario");

                try {
                    option = Validacion.validarEntero(sc);
                    switch (option) {
                        case 1:
                            tipo = Tipo.PRINCIPAL;
                            break;
                        case 2:
                            tipo = Tipo.SECUNDARIO;
                            break;
                        case 3:
                            tipo = Tipo.TERCIARIO;
                            break;
                        default:
                            tipo = Tipo.TERCIARIO;
                    }
                    flag = true;
                } catch (InputMismatchException e) {
                    System.out.println("Debe ingresar un numero entero");
                    sc.nextLine();
                }
            } while (!flag);
            System.out.println("Ingrese la cantidad de asientos del sector: " + (i + 1));
            flag = false;
            do {

                try {
                    cantidadAsientos = sc.nextInt();
                    sc.nextLine();
                    flag = true;
                } catch (InputMismatchException e) {
                    System.out.println("Debe ingresar un numero entero");
                    sc.nextLine();
                }
            } while (!flag);
            ArrayList<Asiento> asientos = new ArrayList<>();
            for (int j = 0; j < cantidadAsientos; j++) {
                asientos.add(new Asiento(j));
            }
            sectores.add(new Sector(nombre, tipo, asientos));
        }
        return sectores;
    }

    public void crearOrganizador(Scanner sc, Boleteria boleteria, String archivo) {
        String nombre, email, contrasenia;
        boolean flagEmail = false;
        boolean flagContrasenia = false;
        System.out.println("Ingrese nombre de organizador");
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
        boleteria.guardarUsuario(new Organizador(nombre, email, contrasenia), archivo);
    }

    /// Probar modificacion
    public void modificarOrganizador(Scanner sc, Boleteria boleteria, String archivo) {
        int organizadorId;
        Organizador organizador;

        System.out.println("Organizadores:\n" + boleteria.mostrarOrganizadores());
        organizadorId = Validacion.validarEntero(sc, "Ingrese id del usuario a modificar");
        if (boleteria.getUsuarios().buscarElementoId(organizadorId) instanceof Organizador) {
            organizador = (Organizador) boleteria.getUsuarios().buscarElementoId(organizadorId);

            String nombre, email, contrasenia;
            boolean flagEmail = false;
            boolean flagContrasenia = false;
            System.out.println("Ingrese nombre de organizador");
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
            organizador.setNombre(nombre);
            organizador.setContrasenia(contrasenia);
            organizador.setEmail(email);
            boleteria.guardarBoleteria(archivo);
        } else System.out.println("El elemento seleccionado no es un organizador");

    }
    public void verMisEventos(Organizador o){
        StringBuilder sb = new StringBuilder();
        System.out.println("Estos son tus eventos: ");
        sb.append("Total de eventos: ").append(o.getEventosCreados().size());
        for (Evento e : o.getEventosCreados()) {
            sb.append("\nId:").append(e.getId()).append("\tNombre: ").append(e.getNombre())
                    .append("\tFunciones disponibles: ").append(e.getFunciones().size());
        }
        System.out.println(sb);
    }
}
