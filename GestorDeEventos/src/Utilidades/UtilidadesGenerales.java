package Utilidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class UtilidadesGenerales {
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
}
