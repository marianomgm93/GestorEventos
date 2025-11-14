/**
 * Excepción lanzada para indicar que un correo electrónico (email)
 * proporcionado no cumple con el formato o las reglas de validación
 * específicas del sistema.
 *
 * Esta excepción es chequeada
 */
package exceptions;

public class EmailInvalidoException extends Exception{

    /**
     * Constructor que crea una nueva instancia de EmailInvalidoException
     * con un mensaje detallado específico.
     *
     * @param message El mensaje detallado (la causa) de la excepción.
     */
    public EmailInvalidoException(String message){
        super(message);
    }
}