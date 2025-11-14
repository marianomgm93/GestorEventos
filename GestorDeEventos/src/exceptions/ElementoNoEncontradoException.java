/**
 * Excepción lanzada para indicar que un elemento esperado no pudo ser
 * localizado dentro de una colección o estructura de datos.
 */
package exceptions;

public class ElementoNoEncontradoException extends RuntimeException {

    /**
     * Constructor que crea una nueva instancia de ElementoNoEncontradoException
     * con un mensaje detallado específico.
     *
     * @param message El mensaje detallado (la causa) de la excepción.
     */
    public ElementoNoEncontradoException(String message) {
        super(message);
    }
}