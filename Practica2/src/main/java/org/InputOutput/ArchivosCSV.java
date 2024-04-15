package org.InputOutput;

import org.Excepciones.DoNotExistException;
import org.Excepciones.ErrorFileAccessException;
import org.Excepciones.ErrorType;
import org.Excepciones.IllegalArgument;
import org.Logica.PoblacionRatones;

import java.io.*;


/**
 * Lee y escribe datos de archivos
 */
public class ArchivosCSV {

    /**
     * Recupera y muestra los archivos previamente creados en la carpeta resources del programa.
     * Permite al usuario elegir qué archivo quiere abrir y lo devuelve.
     * Path: src/main/resources
     * @return Un objeto de tipo File con la localización del archivo que ha seleccionado el usuario.
     * @throws DoNotExistException Si no se han creado archivos previamente
     * @throws ErrorFileAccessException si no se ha podido crear el directorio
     */
    public static File openFile() throws DoNotExistException, ErrorFileAccessException {
        File directorio = new File("src/main/resourcesCSV");
        int indexfile = 0;

        //Comprueba si el directorio está creado y si no, lo crea
        if (!directorio.exists()){
            if(directorio.mkdirs()){
                System.out.println("Se ha creado el directorio");
                throw new DoNotExistException(ErrorType.FileDoNotExist);
            }else{
                throw new ErrorFileAccessException("No se ha podido crear el directorio");
            }
        }else{
            File[] listaFilesDirectorio = directorio.listFiles();
            if(listaFilesDirectorio.length!=0){
                System.out.println("Poblaciones ya creadas");
                int i = 1;
                for(File file: listaFilesDirectorio){
                    System.out.println(i+") "+file.getName());
                    i++;
                }
                System.out.println("Qué archivo quiere abrir: ");
                indexfile = Consola.inputInt()-1;
                while (indexfile<0 || indexfile> listaFilesDirectorio.length-1){
                    System.out.println("Opción no válida, vuelva a elegir: ");
                    indexfile = Consola.inputInt()-1;
                }
                return listaFilesDirectorio[indexfile];
            } else{
                throw new DoNotExistException(ErrorType.FileDoNotExist);
            }
        }
    }
    /**
     * Guarda en un archivo de texto plano una población de ratones dada.
     * El archivo tendrá el mismo nombre que la población. Los datos se almacenarán con formato csv.
     * Si el archivo ya existe, lo sobreescribe.
     * Si el archivo no existe, crea uno nuevo y almacena los valores de la población.
     * @param poblacion La población que se quiere guardar
     * @param f1 La localización del archivo
     * @throws ErrorFileAccessException si ocurre un error en el proceso de escritura
     */
    public static void writePoblacion(PoblacionRatones poblacion, File f1) throws ErrorFileAccessException{
        BufferedWriter bwrite = null;

        try {
            bwrite = new BufferedWriter(new FileWriter(f1));
            if (f1.createNewFile()) {
                System.out.println("Se ha creado el archivo");
            }
            bwrite.write(poblacion.toCSV(","));
            bwrite.close();
            System.out.println("Población guardada con éxito");
        } catch (IOException e) {
            throw new ErrorFileAccessException(e.getMessage());
        }finally {
            try {
                bwrite.close();
            }catch (IOException | NullPointerException ex){
                System.out.println("Error cerrando el flujo de datos");
            }
        }
    }
    /**
     * Lee un archivo dado y devuelve la población contenida en dicho archivo.
     * @param f1 La localización del archivo que se quiere leer
     * @return Un objeto de tipo PoblacionRatones con la población almacenada en f1
     * @throws ErrorFileAccessException si ocurre un error en el proceso de lectura.
     * @throws IllegalArgument si el peso, temperatura o cromosomas no son válidos;
     * si el número de días en la instalación no está entre 0 y 630, o si no es múltiplo de 5;
     * si hay ratones con códigos repetidos.
     */
    public static PoblacionRatones readPoblacion(File f1) throws ErrorFileAccessException, IllegalArgument {
        PoblacionRatones poblacion = null;
        BufferedReader breader = null;

        try {
            breader = new BufferedReader(new FileReader(f1));

            String fromCSV = "";
            String linea;

            while ((linea = breader.readLine())!=null){
                fromCSV += linea+"\n";
            }

            poblacion = PoblacionRatones.fromCSV(fromCSV, ",");

            breader.close();
            System.out.println("Archivo leído con éxito");
            return poblacion;
        } catch (IOException e) {
            throw new ErrorFileAccessException(e.getMessage());
        }finally {
            try {
                breader.close();
            }catch (IOException ex){
                System.out.println("Error cerrando el flujo de datos");
            }
        }
    }


    public static void main(String[] args) {
        ArchivosCSV archivos = new ArchivosCSV();
        File f1 = null;
        PoblacionRatones poblacion = null;
        try {
            f1 = archivos.openFile();
            poblacion = archivos.readPoblacion(f1);
            System.out.println(poblacion);

            PoblacionRatones poblacionRatones = new PoblacionRatones("poblacion1", "MamenCortes", 234);
            //poblacionRatones.addRaton(new Raton(2345, LocalDate.now(), 19.2f, 36.0f, Sexo.HEMBRA, " "));
            //poblacionRatones.addRaton(new Raton(2346, LocalDate.now(), 20f, 37.0f, Sexo.MACHO, " "));

            archivos.writePoblacion(poblacionRatones, new File("src/main/resourcesCSV/"+poblacionRatones.getNombrePoblacion()+".csv"));
        }catch (DoNotExistException | ErrorFileAccessException |IllegalArgument ex){
            System.out.println("Ha ocurrido un error en"+ ex);
        }
    }
}
