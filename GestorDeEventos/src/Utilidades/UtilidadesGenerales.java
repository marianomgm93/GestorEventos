package Utilidades;

import exceptions.ContraseniaInvalidaException;
import model.Boleteria;
import model.Usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Scanner;

public class UtilidadesGenerales {
    private static final String LINEA = "═══════════════════════════════════════════════════════════════";

    public static String formatearFecha(LocalDateTime fecha) {
        if (fecha == null) {
            return "";
        }
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("d")
                .appendLiteral(" ")
                .appendPattern("MMM")
                .appendPattern(" yyyy")
                .appendLiteral(", ")
                .appendPattern("HH:mm")
                .toFormatter(new Locale("es", "ES"));

        return fecha.format(formatter);
    }



    public static boolean cambiarContrasena(Scanner sc, Usuario usuario, String archivo, Boleteria boleteria) {
        System.out.println(LINEA);
        System.out.println("                     CAMBIO DE CONTRASEÑA");
        System.out.println(LINEA);

        System.out.print("Ingrese su contraseña actual: ");
        String actual = sc.nextLine();

        if (!usuario.getContrasenia().equals(actual)) {
            System.out.println("Contraseña actual incorrecta.");
            System.out.println(LINEA);
            return false;
        }

        String nueva;
        String confirmar;

        do {
            System.out.print("Nueva contraseña: ");
            nueva = sc.nextLine();
            System.out.print("Repita la nueva contraseña: ");
            confirmar = sc.nextLine();

            if (!nueva.equals(confirmar)) {
                System.out.println("Las contraseñas no coinciden. Intente nuevamente.");
                continue;
            }

            try {
                Validacion.validarContrasena(nueva);
                break;
            } catch (ContraseniaInvalidaException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (true);

        usuario.setContrasenia(nueva);
        boleteria.guardarBoleteria(archivo);

        System.out.println("Contraseña cambiada exitosamente.");
        System.out.println(LINEA);
        return true;
    }

}
