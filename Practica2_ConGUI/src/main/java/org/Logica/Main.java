package org.Logica;

import org.Excepciones.DoNotExistException;
import org.Excepciones.ErrorFileAccessException;
import org.Excepciones.ErrorType;
import org.Excepciones.IllegalArgument;
import org.InputOutput.ArchivosCSV;
import org.InputOutput.Consola;
import org.Interfaz.Menu;
import org.Logica.PoblacionRatones;
import org.Logica.Raton;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Clase principal. Se encarga del control de flujo del programa, manejo del menú y de excepciones.
 * @author Mamen Cortés Navarro
 */
public class Main {
    public static void main(String[] args) {
        int op = 0;
        TreeMap<String, PoblacionRatones> poblaciones = new TreeMap<>();
        ArrayList<File> files = new ArrayList<File>();
        do{
            op = Consola.menu();
            switch (op){
                case 1: { //Abrir una población
                    File file = null;
                    try{
                        //Preguntar al usuario qué archivo quiere abrir
                        file = ArchivosCSV.openFile();
                    }catch (DoNotExistException notExistException){
                        System.out.println("No hay archivos creados");
                    }catch (ErrorFileAccessException ex){
                        System.out.println(ex.getError());
                    }
                    //Almacenar la población del archivo en el array de poblaciones
                    if(file != null){
                        if(files.contains(file)){
                            System.out.println("Este archivo ya se ha abierto");
                            break;
                        }
                        files.add(file);
                        try {
                            PoblacionRatones p1 = ArchivosCSV.readPoblacion(file);
                            poblaciones.put(p1.getNombrePoblacion(), p1);
                        }catch (ErrorFileAccessException errorFileAccessException){
                            System.out.println("Ha habido un problema leyendo el archivo, vuelva a intentarlo más tarde");
                        }catch (NullPointerException npex){
                            System.out.println("Ha habido un problema leyendo el nombre de la poblacion");
                        }catch (IllegalArgument iAex){
                            System.out.println("Error creando la población");
                            System.out.println(iAex.getMessage());
                        }
                    }

                    break;}
                case 2: {

                    //Crear una nueva población y añadirla al array de poblaciones
                    try {
                        PoblacionRatones poblacion = Consola.crearPoblacion();
                        poblaciones.put(poblacion.getNombrePoblacion(), poblacion);
                    }catch (IllegalArgument iAex){
                        System.out.println("Error creando la población");
                        System.out.println(iAex.getMessage());
                    }


                    break;}
                case 3:{//crear una población virtual
                    try{
                        PoblacionRatones poblacion = Consola.crearPoblacionVirtual();
                        poblaciones.put(poblacion.getNombrePoblacion(), poblacion);
                    } catch (IllegalArgument iAex) {
                        System.out.println(iAex.getMessage());
                    }
                    break; }
                case 4: {//Añadir ratón a una población

                    try{
                        if(!poblaciones.isEmpty()) {
                            String nombrePoblacion = Consola.quePoblacion(poblaciones);
                            if (poblaciones.containsKey(nombrePoblacion)) {
                                poblaciones.get(nombrePoblacion).addRaton(Consola.nuevoRaton());
                            }
                        }
                    }catch (DoNotExistException doNotExistException){
                        if (doNotExistException.getErrorType().equals(ErrorType.PoblacionRatonesDoNotExist)){
                            System.out.println("No hay ninguna población creada");
                            System.out.println("Cree una antes de continuar");
                        }else if(doNotExistException.getErrorType().equals(ErrorType.PoblacionNotFound)){
                            System.out.println("No se ha encontrado la población introducida");
                        }
                    }catch (IllegalArgument iAex){
                        if(iAex.getErrorType().equals(ErrorType.IllegalPeso)){
                            System.out.println("El peso introducido no es válido. Debe tomar valores entre 0 y 50 gramos");
                        }else if(iAex.getErrorType().equals(ErrorType.IllegalTemperatura)){
                            System.out.println("La temperatura introducida no es válida. Debe tomar valores entre 20 y 40º");
                        } else if (iAex.getErrorType().equals(ErrorType.RatonAlreadyExistsWithThisID)){
                            System.out.println("Ya existe un ratón con el código de referencia introducido");
                        }
                    }

                    break;}
                case 5: {//Listar códigos de referencia de todos los ratones

                    try{
                        Consola.outputRatonesOrdenados(poblaciones);
                    }catch (DoNotExistException doNotExistException){
                        if (doNotExistException.getErrorType().equals(ErrorType.PoblacionRatonesDoNotExist)){
                            System.out.println("No hay ninguna población creada");
                            System.out.println("Cree una antes de continuar");
                        }else if(doNotExistException.getErrorType().equals(ErrorType.PoblacionNotFound)){
                            System.out.println("No se ha encontrado esa población");
                        }
                    }

                    break;}
                case 6: {//eliminar un ratón según su número de referencia

                    try{
                        if(!poblaciones.isEmpty()) {
                            String nombrePoblacion = Consola.quePoblacion(poblaciones);
                            if (poblaciones.containsKey(nombrePoblacion)) {
                                poblaciones.get(nombrePoblacion).deleteRaton(Consola.inputCodeRatonAEliminar(poblaciones.get(nombrePoblacion)));
                            }
                        }
                    }catch (DoNotExistException doNotExistException){
                        if (doNotExistException.getErrorType().equals(ErrorType.PoblacionRatonesDoNotExist)){
                            System.out.println("No hay ninguna población creada");
                            System.out.println("Cree una antes de continuar");
                        }else if(doNotExistException.getErrorType().equals(ErrorType.PoblacionNotFound)){
                            System.out.println("No se ha encontrado esa población");
                        }else if(doNotExistException.getErrorType().equals(ErrorType.RatonNotFound)){
                            System.out.println("No se ha encontrado el ratón identificado con ese numero de referencia");
                        }
                    }

                    break;}
                case 7: {// Modificar valores de peso, temperatura o texto de un ratón

                    try{
                        if(!poblaciones.isEmpty()) {
                            String nombrePoblacion = Consola.quePoblacion(poblaciones);
                            if (poblaciones.containsKey(nombrePoblacion)) {
                                Raton raton = Consola.modificarRaton(poblaciones.get(nombrePoblacion));
                                poblaciones.get(nombrePoblacion).modificarRaton(raton);
                            }
                        }
                    }catch (DoNotExistException doNotExistException){
                        if (doNotExistException.getErrorType().equals(ErrorType.PoblacionRatonesDoNotExist)){
                            System.out.println("No hay ninguna población creada");
                            System.out.println("Cree una antes de continuar");
                        }else if(doNotExistException.getErrorType().equals(ErrorType.PoblacionNotFound)){
                            System.out.println("No se ha encontrado esa población");
                        }else if(doNotExistException.getErrorType().equals(ErrorType.RatonNotFound)){
                            System.out.println("El código introducido no corresponde con el de ningún ratón");
                        }
                    }catch (IllegalArgument iAex){
                        if(iAex.getErrorType().equals(ErrorType.IllegalPeso)){
                            System.out.println("El peso introducido no es válido. Debe tomar valores entre 0 y 50 gramos");
                        }else if(iAex.getErrorType().equals(ErrorType.IllegalTemperatura)) {
                            System.out.println("La temperatura introducida no es válida. Debe tomar valores entre 20 y 40º");
                        }
                    }
                    break;}
                case 8: {//Ver información detallada

                    try{
                        Consola.verInfoDetallada(poblaciones);
                    }catch (DoNotExistException doNotExistException){
                        if (doNotExistException.getErrorType().equals(ErrorType.PoblacionRatonesDoNotExist)){
                            System.out.println("No hay ninguna población creada");
                            System.out.println("Cree una antes de continuar");
                        }else if(doNotExistException.getErrorType().equals(ErrorType.PoblacionNotFound)){
                            System.out.println("No se ha encontrado esa población");
                        }else if (doNotExistException.getErrorType().equals(ErrorType.RatonNotFound)){
                            System.out.println("No se ha encontrado ningún ratón que corresponda con el código introducido");
                        }
                    }
                    break;}
                case 9:{//simulación de Montecarlo

                    System.out.println("Simulación de Montecarlo");
                    try {
                        if(!poblaciones.isEmpty()) {
                            String nombrePoblacion = Consola.quePoblacion(poblaciones);
                            if (poblaciones.containsKey(nombrePoblacion)) {
                                Consola.printSimulacion(poblaciones.get(nombrePoblacion));
                            }
                        }
                    }catch (DoNotExistException doNotExistException){
                        if(doNotExistException.getErrorType().equals(ErrorType.PoblacionNotFound)){
                            System.out.println("No se ha encontrado esa población");
                        }else if(doNotExistException.getErrorType().equals(ErrorType.PoblacionDoesNotContainRatones)){
                            System.out.println("La población no contiene ratones");
                        }
                    }catch (IllegalArgument iAex){
                        System.out.println(iAex.getMessage());
                    }

                    break; }
                case 10: {//Guardar

                    System.out.println("Guardar población:");
                    if(!poblaciones.isEmpty()){
                        //El nombre del archivo será igual al nombre de la población
                        String nombrePoblacion;
                        try{
                            nombrePoblacion = Menu.quePoblacion(poblaciones);
                        } catch (DoNotExistException dneException){
                            if(dneException.getErrorType().equals(ErrorType.PoblacionNotFound)){
                                System.out.println("No se ha encontrado esa población");
                            }
                            break;
                        }
                        File file = new File("src/main/resourcesCSV/"+nombrePoblacion+".csv");

                        //Si el archivo existe
                        if(files.contains(file)){
                            try {
                                //Guarda la población en un archivo
                                ArchivosCSV.writePoblacion(poblaciones.get(nombrePoblacion), file);
                            }catch (ErrorFileAccessException errorFileAccessException){
                                System.out.println("Ha habido un problema guardando los datos en el archivo, vuelva a intentarlo más tarde");
                            }

                            //Sale del switch -> vuelve a sacar el menú
                            break;
                        }//Si no existe, salta a la opción de Guardar como
                    }else{
                        System.out.println("No hay ninguna población creada");
                        System.out.println("Cree una antes de guardar");
                        break;
                    }
                }
                case 11: {//Guardar como
                    System.out.println("Guardar como:");
                    //Mientras que haya poblaciones abiertas o creadas
                    if(!poblaciones.isEmpty()){
                        //El nombre del archivo será igual al nombre de la población
                        String nombrePoblacion;
                        try{
                            nombrePoblacion = Consola.quePoblacion(poblaciones);
                        } catch (DoNotExistException dneException){
                            if(dneException.getErrorType().equals(ErrorType.PoblacionNotFound)){
                                System.out.println("No se ha encontrado esa población");
                            }
                            break;
                        }

                        File file = new File("src/main/resourcesCSV/"+nombrePoblacion+".csv");

                        //Guardar la población en un archivo
                        //No se comprueba si el archivo existe porque se asume que se creará uno nuevo
                        if(poblaciones.containsKey(nombrePoblacion)){
                            try {
                                //Guarda la población en un archivo
                                ArchivosCSV.writePoblacion(poblaciones.get(nombrePoblacion), file);
                            }catch (ErrorFileAccessException errorFileAccessException){
                                System.out.println("Ha habido un problema guardando los datos en el archivo, vuelva a intentarlo más tarde");
                            }
                        }

                    }else{
                        System.out.println("No hay ninguna población creada");
                        System.out.println("Cree una antes de guardar");
                    }
                    break;
                }
            }
        }while (op>0 && op<=11);
    }
}