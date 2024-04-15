package org.Excepciones;


/**
 * Excepción que se lanza cuando no existe o no se encuentra un objeto, generalmente en una lista.
 * @author Mamen Cortés Navarro
 */
public class DoNotExistException extends RuntimeException{
    private ErrorType errorType;

    /**
     * Constructor de la clase
     * @param errorType De tipo ErrorType (enum), define la causa del error
     */
    public DoNotExistException(ErrorType errorType){
        this.errorType = errorType;

    }

    /**
     * Getter del atributo privado errorType.
     * @return Un objeto de tipo ErrorType conteniendo el valor de la variable errorType
     */
    public ErrorType getErrorType() {
        return errorType;
    }
}
