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
                // Día del mes sin cero a la izquierda
                .appendPattern("d")
                // Espacio
                .appendLiteral(" ")
                // Mes en texto abreviado (ene, feb, mar...)
                .appendPattern("MMM")
                // Año completo
                .appendPattern(" yyyy")
                // Separador de fecha y hora
                .appendLiteral(", ")
                // Hora y minutos con dos dígitos
                .appendPattern("HH:mm")
                .toFormatter(new Locale("es", "ES")); // Importante: locale español

        return fecha.format(formatter);
    }
}
