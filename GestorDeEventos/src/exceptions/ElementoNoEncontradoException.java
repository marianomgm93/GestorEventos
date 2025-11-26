/**
 * Excepción lanzada para indicar que un elemento esperado no pudo ser
 * localizado dentro de una colección o estructura de datos.
 */
package exceptions;

public class ElementoNoEncontradoException extends RuntimeException {
    
    public ElementoNoEncontradoException(String message) {
        super(message);
    }
}