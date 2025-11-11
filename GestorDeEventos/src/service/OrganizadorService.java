package service;

import exceptions.NumeroInvalidoException;
import model.Categoria;
import model.Evento;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OrganizadorService {
    public void nuevoEvento(Scanner sc) {

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
            }
        } while (opcion < 1 && opcion > 5);
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

    }
}
