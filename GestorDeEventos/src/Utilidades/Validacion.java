package Utilidades;

import exceptions.NumeroInvalidoException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Validacion {

    public static int validarEntero(Scanner sc,String mensaje){
        int numero = 0;
        boolean flag=false;
        do {
            System.out.println(mensaje);
            try {
                numero = sc.nextInt();
                sc.nextLine();
                flag=true;
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un numero entero");
                sc.nextLine();
            }
        } while (!flag);
        return numero;
    }
}
