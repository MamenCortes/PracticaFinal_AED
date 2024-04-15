package org.Excepciones;

/**
 * Posibles causas de error
 * @author Mamen Cortés Navarro
 */
public enum ErrorType {
    /**
     * No hoy ficheros previamente creados
     */
    FileDoNotExist,
    /**
     * No hay poblaciones previamente creadas
     */
     PoblacionRatonesDoNotExist,
    /**
     * El ratón especificado no se encuentra
     */
    RatonNotFound,
    /**
     * La población está vacía, no contiene ratones
     */
    PoblacionDoesNotContainRatones,
    /**
     * Ya existe un ratón con el mismo código de referencia
     */
    RatonAlreadyExistsWithThisID,
    /**
     * La población especificada no se encuentra
     */
    PoblacionNotFound,
    /**
     * El formato introducido para la fecha no es correcto
     */
    IllegalDate,
    /**
     * Los cromosomas introducidos no son válidos o no corresponden con el sexo del ratón
     */
    IllegalCromosomas,
    /**
     * La temperatura introducida no es válida
     */
    IllegalTemperatura,
    /**
     * El ratón es de un sexo diferente al requerido en la función
     */
    IllegalSex,
    /**
     * El peso introducido no es válido
     */
    IllegalPeso,
    /**
     * El número de días que los ratones permanecerán en la instalación no es válido
     */
    IllegalDiasEnInstalacion,
    /**
     * La cadena de caracteres en formato CSV que se quiere utilizar para crear un objeto no es válida
     */
    IllegalCSV,
    /**
     * El porcentaje introducido no es válido (0-100)
     */
    IllegalPercentage
}
