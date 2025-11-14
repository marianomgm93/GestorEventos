/**
 * Excepción lanzada para indicar que se intentó registrar o agregar
 * un {@code Usuario} que ya existe dentro de una colección o estructura de datos.
 *
 * Esta excepción es <b>no chequeada</b>
 */
package exceptions;

public class UsuarioRepetidoException extends RuntimeException {

    /**
     * Constructor que crea una nueva instancia de UsuarioRepetidoException
     * con un mensaje detallado específico.
     *
     * @param message El mensaje detallado (la causa) de la excepción.
     */
    public UsuarioRepetidoException(String message) {
        super(message);
    }
}