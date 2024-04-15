package org.Excepciones;

/**
 * Excepción que es lanzada al interactuar con la interfaz gráfica
 */
public class GUIExceptions extends RuntimeException{
    private final String message;
    private final GUIError guiError;

    /**
     * Constructor de la clase
     * @param guiError el tipo de error
     * @param message cualquier información adicional sobre el error
     */
    public GUIExceptions(GUIError guiError, String  message){

        this.message = message;
        this.guiError = guiError;
    }

    /**
     * Devuelve la información adicional/ el mensaje de error
     * @return el mensaje de error
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Devuelve el tipo de error
     * @return el tipo de error
     */
    public GUIError getGuiError() {
        return guiError;
    }
}
