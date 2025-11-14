/**
 * Excepción lanzada para indicar que un valor numérico es inválido,
 * ya sea por formato incorrecto, por estar fuera de un rango aceptable
 * o por cualquier otra regla de negocio.
 *
 * Esta excepción es <b>no chequeada</b>
 */
package exceptions;

public class NumeroInvalidoException extends RuntimeException {

    /**
     * Constructor que crea una nueva instancia de NumeroInvalidoException
     * con un mensaje detallado específico.
     *
     * @param message El mensaje detallado (la causa) de la excepción.
     */
    public NumeroInvalidoException(String message) {
        super(message);
    }
}