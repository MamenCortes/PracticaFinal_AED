package org.Excepciones;

/**
 * Enumerador que representa posibles errores al interactuar con una interfaz gráfica
 */
public enum GUIError {
    /**
     * Representa cuando en un cuadro de diálogo se selecciona
     * una opción de cancelar/ok, etc. que no generará ninguna
     * acción más que cerrar la ventana
     */
    CanceledOperation,
    /**
     * Cuando no se ha introducido ningún valor en algún campo de texto obligatorio
     */
    EmptyTextField
}
