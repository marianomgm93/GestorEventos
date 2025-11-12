package service;

import exceptions.NumeroInvalidoException;
import model.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OrganizadorService {
    public void nuevoEvento(Scanner sc, Organizador organizador) {

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
        organizador.getEventosCreados().add(evento);

    }

    public void agregarFuncion(Scanner sc, Evento evento) {
        String hora;
        double precio = 0;
        boolean flag = false;
        System.out.println("Ingrese hora de la funcion");
        hora = sc.nextLine();
        System.out.println("Ingrese el precio base");
        do {
            try {
                precio = sc.nextDouble();
                sc.nextLine();
                flag = true;
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un numero");
                sc.nextLine();
            }
        } while (!flag);
        Recinto recinto = nuevoRecinto(sc);
        Funcion funcion = new Funcion(hora, recinto, precio);
        evento.getFunciones().add(funcion);
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
                    option = sc.nextInt();
                    sc.nextLine();
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
                asientos.add(new Asiento());
            }
            sectores.add(new Sector(nombre, tipo, asientos));
        }
        return sectores;
    }
}
