package org.Excepciones;

/**
 * Excepción que es lanzada cuando ocurre un error en la lectura o escritura de datos en un fichero
 * A su vez, sirve para aunar varias excepciones en una sola.
 * @author Mamen Cortés Navarro
 */
public class ErrorFileAccessException extends RuntimeException{
    private String error;

    /**
     * Constructor de la clase
     * @param error El mensaje de error capturado por otras excepciones
     */
    public ErrorFileAccessException(String error){
        this.error = error;
    }

    /**
     * Getter del atributo error
     * @return devuelve un string con el mensaje de error
     */
    public String getError(){
        return error;
    }
}
