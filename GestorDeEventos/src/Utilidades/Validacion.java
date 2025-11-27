package Utilidades;

import exceptions.*;
import model.Boleteria;
import model.Evento;
import model.Funcion;
import model.Usuario;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Validacion {

    public static int validarEntero(Scanner sc, String mensaje) {
        int numero = 0;
        boolean flag = false;
        do {
            System.out.println(mensaje);
            try {
                numero = sc.nextInt();
                sc.nextLine();
                flag = true;
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un numero entero");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("El numero ingresado es invalido");
                sc.nextLine();
            }
        } while (!flag);
        return numero;
    }

    public static int validarEntero(Scanner sc) {
        int numero = 0;
        boolean flag = false;
        do {
            try {
                numero = sc.nextInt();
                sc.nextLine();
                flag = true;
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un numero entero");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("El numero ingresado es invalido");
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

    public static <T> boolean repetido(T objeto, List<T> lista) {
        return lista.contains(objeto);
    }

    public static boolean validarUsuario(Usuario usuario, String contrasenia) throws UsuarioInvalidoException {
        if (!usuario.getContrasenia().equals(contrasenia)) throw new UsuarioInvalidoException("Contrasenia incorrecta");
        return true;
    }

    public static LocalDateTime validarLocalDateTime(Scanner sc) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaIngresada;

        while (true) {
            int anio = validarEntero(sc, "Ingrese el año (e.g. 2025): ", 1, 2050);
            int mes = validarEntero(sc, "Ingrese el mes (1-12): ", 1, 12);
            int dia = validarEntero(sc, "Ingrese el día (1-31): ", 1, 31);

            int hora = validarEntero(sc, "Ingrese la hora (0-23): ", 0, 23);
            int minuto = validarEntero(sc, "Ingrese los minutos (0-59): ", 0, 59);

            // Validar que la fecha exista
            if (!fechaValida(anio, mes, dia)) {
                System.out.println("La fecha ingresada no existe. Intente nuevamente.");
            } else {
                fechaIngresada = LocalDateTime.of(anio, mes, dia, hora, minuto);

                // Validar que sea futura
                if (fechaIngresada.isAfter(ahora)) {
                    return fechaIngresada;
                } else {
                    System.out.println("La fecha debe ser FUTURA. Intente nuevamente.");
                }
            }
        }
    }

    private static boolean fechaValida(int anio, int mes, int dia) {
        boolean flag = false;
        try {
            LocalDateTime.of(anio, mes, dia, 0, 0);
            flag = true;
        } catch (Exception e) {
            System.out.println("La fecha ingresada es invalida");
        }
        return flag;
    }

    public static int validarEntero(Scanner sc, String mensaje, int min, int max) {
        int valor;

        while (true) {
            try {
                System.out.print(mensaje);
                valor = Integer.parseInt(sc.nextLine());

                if (valor < min || valor > max) {
                    System.out.println("Valor fuera de rango. Debe estar entre " + min + " y " + max + ".");
                    continue;
                }
                return valor;

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número válido.");
            }
        }
    }

    /**
     * Retorna verdadero si no existe una funcion en el mismo recinto a la misma hora
     *
     * @param boleteria
     * @param funcion
     * @return
     */
    public static boolean validarFuncion(Boleteria boleteria, Funcion funcion) {
        for (Evento e : boleteria.getEventos().getElementos()) {
            if (e.isActivo()) {
                if (e.getFunciones().contains(funcion)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static double validarDouble(Scanner sc, String mensaje, double minimo) {
        double valor;
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();
            try {
                valor = Double.parseDouble(entrada);
                if (valor < minimo) {
                    System.out.printf("Error: el valor debe ser mayor o igual a %.2f.%n", minimo);
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número válido (ej: 15000.50).");
            }
        }
    }
}
