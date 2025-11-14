package Utilidades;

import exceptions.ContraseniaInvalidaException;
import exceptions.EmailInvalidoException;
import exceptions.NumeroInvalidoException;

import java.util.InputMismatchException;
import java.util.List;
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
        public static boolean validarEmail(String email) throws EmailInvalidoException {
            // Verificar que contenga '@' y '.'
            if (email == null || email.isEmpty()) {
                throw new EmailInvalidoException("El email no puede ser nulo");
            }

            int indiceArroba = email.indexOf('@');
            int indicePunto = email.lastIndexOf('.');

            // Debe tener '@' y '.' y el '.' debe estar después del '@'
            if (!(indiceArroba > 0 && indicePunto > indiceArroba && (indicePunto < email.length() - 1))) {
                throw new EmailInvalidoException("El email debe contener un arroba y un punto luego del arroba sin estar al final");
            }

            return true;
        }

    public static boolean validarContrasena(String contrasenia) throws ContraseniaInvalidaException {
        // Verificar que no sea nula o vacía
        if (contrasenia == null || contrasenia.isEmpty()) {
            throw new ContraseniaInvalidaException("La contraseña no puede ser nula o vacía");
        }

        // Verificar longitud mínima
        if (contrasenia.length() < 8) {
            throw new ContraseniaInvalidaException("La contraseña debe tener al menos 8 caracteres");
        }

        // Verificar que tenga al menos una letra y un número
        boolean tieneLetra = false;
        boolean tieneNumero = false;

        for (char c : contrasenia.toCharArray()) {
            if (Character.isLetter(c)) {
                tieneLetra = true;
            } else if (Character.isDigit(c)) {
                tieneNumero = true;
            }
        }

        if (!tieneLetra) {
            throw new ContraseniaInvalidaException("La contraseña debe contener al menos una letra");
        }

        if (!tieneNumero) {
            throw new ContraseniaInvalidaException("La contraseña debe contener al menos un número");
        }

        return true;
    }
    public static <T> boolean repetido(T objeto, List<T> lista){
        return lista.contains(objeto);
    }
}
