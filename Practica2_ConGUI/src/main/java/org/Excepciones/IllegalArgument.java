package org.Excepciones;

/**
 * Excepción que es lanzada cuando se tratan de introducir valores no válidos
 * A su vez, sirve para aunar varias excepciones en una sola.
 * @author Mamen Cortés Navarro
 */
public class IllegalArgument extends Exception{

    private final ErrorType errorType;
    private final String message;

    /**
     * Constructor que permite especificar el tipo de error e información a cerca del origen del error
     * @param errorType el tipo de error
     * @param message información adicional
     */
    public IllegalArgument(String message, ErrorType errorType){
        this.errorType = errorType;
        this.message = message;
    }

    /**
     * Devuelve una cadena de caracteres con el tipo de error
     * @return el tipo de error que generó la excepción
     */
    @Override
    public String toString() {
        return errorType.toString();
    }

    /**
     * Devuelve el tipo de error que generó la excepción
     * @return el tipo de error
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    /**
     * Devuelve la información adicional a cerca del origen de la excepción
     * @return la información adicional
     */
    public String getMessage(){
        return message;
    }
}
