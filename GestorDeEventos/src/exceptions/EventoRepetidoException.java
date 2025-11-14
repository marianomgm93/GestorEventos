/**
 * Excepción lanzada para indicar que se intentó registrar o agregar
 * un {@code Evento} que ya existe dentro de una colección o estructura de datos.
 *
 * Esta excepción es <b>no chequeada</b>
 */
package exceptions;

public class EventoRepetidoException extends RuntimeException {

    /**
     * Constructor que crea una nueva instancia de EventoRepetidoException
     * con un mensaje detallado específico.
     *
     * @param message El mensaje detallado (la causa) de la excepción.
     */
    public EventoRepetidoException(String message) {
        super(message);
    }
}