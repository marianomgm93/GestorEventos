/**
 * Excepción lanzada para indicar que una contraseña (contraseña)
 * proporcionada por el usuario o en un proceso es inválida.
 * <p>
 * Esta excepción es chequeada.
 * en la cláusula {@code throws} del método que la lanza o ser capturada.
 */
package exceptions;

public class ContraseniaInvalidaException extends Exception{

    /**
     * Constructor que crea una nueva instancia de ContraseniaInvalidaException
     * con un mensaje detallado específico.
     *
     * @param message El mensaje detallado (la causa) de la excepción.
     */
    public ContraseniaInvalidaException(String message){
        super(message);
    }
}