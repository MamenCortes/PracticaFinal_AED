package org.InputOutput;

import org.Excepciones.DoNotExistException;
import org.Excepciones.ErrorType;
import org.Excepciones.IllegalArgument;
import org.Logica.PoblacionRatones;
import org.Logica.Raton;
import org.Logica.Sexo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


/**
 * Maneja la entrada y salida de datos por teclado, además de ejecutar las acciones seleccionadas en el menú principal.
 * @author Mamen Cortés Navarro
 */
public class Consola {

    /**
     * Recupera el texto escrito por teclado por el usuario
     * @return la cadena de caracteres introducida
     */
    public static String input(){
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        try{
            input = console.readLine();
        } catch (IOException e) {
            System.out.println("Error en el flujo de entrada");
        }
        return input;
    }

    /**
     * Lee un número entero por pantalla. Lo sigue pidiendo de manera
     * recursiva siempre que el valor introducido no sea un número entero.
     * @return el número entero introducido por pantalla.
     */
    public static int inputInt(){
        int entero;
        try{
            entero = Integer.parseInt(input());
        }catch (NumberFormatException nfex){
            System.out.println("El tipo de datos introducido no es correcto, debe ser un numero entero.");
            entero = inputInt();
        }
        return entero;
    }

    /**
     * Lee un número real por pantalla. Lo sigue pidiendo de manera recursiva siempre que el valor introducido no sea un número real.
     * @return el número real introducido por pantalla.
     */
    public static float inputFloat(){
        float real = -1;
        try{
            real = Float.parseFloat(input());
        }catch (NumberFormatException nfex){
            System.out.println("El tipo de datos introducido no es correcto, debe ser un numero real.");
            real = inputFloat();
        }
        return real;
    }

    /**
     * Pide al usuario que introduzca por teclado el sexo del ratón. Lo sigue pidiendo siempre que la opción introducida no sea válida.
     * @return el sexo del ratón
     */
    public static Sexo inputSexo(){
        System.out.println("Introduzca M si el ratón es Macho o H si es Hembra");
        String sexo = input();
        //Verificación de opción válida
        while (!sexo.equals("M") && !sexo.equals("H")){
            System.out.println("Introduzca una opción válida: ");
            sexo = input();
        }
        if (sexo.equals("M")){
            return Sexo.MACHO;
        }else{
            return Sexo.HEMBRA;
        }
    }

    /**
     * Pide al usuario la fecha de nacimiento del ratón, la convierte y la devuelve. Si el formato no es correcto o no se ha convertido adecuadamente, la vuelve a pedir de manera recursiva.
     * @return la fecha de nacimiento introducida
     */
    public static LocalDate inputNacimiento(){
        System.out.println("Introduce la fecha de nacimiento en la forma yyyy-MM-dd: ");
        LocalDate nacimiento = null;
        while (nacimiento == null){ //Repetir hasta que la fecha introducida sea válida
            try {
                nacimiento=LocalDate.parse(input(), DateTimeFormatter.ISO_DATE);
            }// If the String pattern is invalid
            catch (IllegalArgumentException e) {
                System.out.println("El formato de la fecha es incorrecto, vuelva a introducirla en la forma yyyy-MM-dd");
                nacimiento = inputNacimiento();
            } // If the String was unable to be parsed.
            catch (DateTimeParseException e) {
                //throw new ErrorFileAccessException(ErrorType.LocalDateParseError);
                System.out.println("Ha ocurrido un error, vuelva a introducir la fecha");
                nacimiento = inputNacimiento();
            }
        }
        return nacimiento;
    }

    /**
     * Pregunta al usuario si quiere que un cromosoma contenga o no una mutación., la convierte y la devuelve. Si el formato introducido no es correcto, lo vuelve a pedir de manera recursiva.
     * @return true si quiere que esté mutado, false si no lo quiere.
     */
    private static boolean inputCromosoma(){
        System.out.println("Quieres que el cromosoma esté mutado? Introduzca s/n: ");
        String mut1 = input();
        while (!(mut1.equals("s")||mut1.equals("n"))){
            System.out.println("Opción no válida, introduzca una opción válida:");
            mut1 = input();
        }
        return mut1.equals("s"); //las únicas dos respuestas posibles son s/n
    }

    /**
     * Saca por pantalla el menú principal y pide al usuario que seleccione una acción.
     * @return un número entero con la opción elegida
     */
    public static int menu(){
        int op = 0;

        //Sacar el menú por pantalla
        System.out.println("Menu: ");
        System.out.println("1) Abrir un archivo");
        System.out.println("2) Crear una nueva población");
        System.out.println("3) Crear una población virtual");
        System.out.println("4) Añadir un ratón a una población existente");
        System.out.println("5) Listar los códigos de referencia de los ratones de una población");
        System.out.println("6) Eliminar un ratón de una población");
        System.out.println("7) Modificar los datos de un ratón");
        System.out.println("8) Ver información de un ratón");
        System.out.println("9) Simulación de Montecarlo");
        System.out.println("10) Guardar");
        System.out.println("11) Guardar como");
        System.out.println("Seleccione una opción");
        System.out.println("En el caso de que quiera terminar el programa, introduzca una opción no válida");
        op = inputInt();
        return op;
    }
    /**
     * Saca por pantalla un menú con los atributos que se pueden modificar en un ratón
     * @return un entero con la opción elegida
     */
    public static int menuModificarRaton(){
        System.out.println("Qué variable quiere modificar: ");
        System.out.println("1) Peso");
        System.out.println("2) Temperatura");
        System.out.println("3) Campo de texto");
        int op = inputInt();
        return op;
    }

    /**
     * Saca por pantalla un menú con las posibles criterios de ordenación de los ratones
     * @return la opción del menú elegida
     */
    public static int menuOrdenRatones(){
        int op =0;
        System.out.println("De qué manera quiere listar los códigos?");
        System.out.println("1) Según el número de referencia");
        System.out.println("2) Según la fecha de nacimiento");
        System.out.println("3) De mayor a menor peso");
        op = inputInt();
        return op;
    }
    /**
     * Pregunta al usuario la población con la que quiere trabajar, sacando por pantalla una lista con los nombres de aquellas abiertas
     * Devuelve el nombre de la población introducido siempre que esta exista
     * @param poblaciones Un TreeMap conteniendo todas las poblaciones abiertas. La clave será el nombre de la población
     * @return el nombre de la población seleccionada
     * @throws DoNotExistException si el nombre introducido por el usuario no corresponde con el de ninguna población
     */
    public static String quePoblacion(TreeMap<String, PoblacionRatones> poblaciones) throws DoNotExistException{
        System.out.println("Poblaciones existentes: ");
        for (PoblacionRatones poblacionRatones : poblaciones.values()) {
            System.out.println("- " + poblacionRatones.getNombrePoblacion());
        }
        System.out.println("A qué población quiere acceder: ");
        String targetPoblacion = input();

        if(poblaciones.containsKey(targetPoblacion)){
            return targetPoblacion;
        }else{
            throw new DoNotExistException(ErrorType.PoblacionNotFound);
        }
    }

    /**
     * Saca por pantalla la información de todos los ratones pertenecientes a una población dada en formato CSV.
     * Estos se mostrarán ordenados o bien por su código de referencia, por su fecha de nacimiento o de mayor a menor peso.
     * @param poblaciones Una lista que conteniendo todas las poblaciones abiertas
     */
    public static void outputRatonesOrdenados(TreeMap<String, PoblacionRatones> poblaciones) throws DoNotExistException{
        if(!poblaciones.isEmpty()){

            //En el caso de que la función lanzase una excepción, se relanzaría directamente al main
            String nombrePoblacion = quePoblacion(poblaciones);;

            if(poblaciones.containsKey(nombrePoblacion)){
                int op = menuOrdenRatones();
                System.out.println("\nRatones: -------------------");
                Set<Raton> ratonesOrdenados;
                switch (op){
                    case 1: {
                        String[] ratonesToCSV = poblaciones.get(nombrePoblacion).toCSV(",").split("\n");
                        System.out.println("Código Referencia, fecha nacimiento, peso, temperatura, sexo, cromosoma1, cromosoma2, Información adicional");
                        for (int i=1; i< ratonesToCSV.length; i++){
                            System.out.println("Ratón "+i+": "+ratonesToCSV[i]);
                        }
                        break;
                    }
                    case 2:{
                        ratonesOrdenados = poblaciones.get(nombrePoblacion).getRatonesOrdenadosPorNacimiento();
                        Iterator<Raton> iterator = ratonesOrdenados.iterator();
                        int i = 1;
                        System.out.println("Código Referencia, fecha nacimiento, peso, temperatura, sexo, cromosoma1, cromosoma2, Información adicional");
                        while (iterator.hasNext()){
                            System.out.println("Ratón "+i+": "+iterator.next().toCSV(","));
                            i++;
                        }
                        break;
                    }
                    case 3:{
                        ratonesOrdenados = poblaciones.get(nombrePoblacion).getRatonesOrdenadosPorPesoDescendente();
                        Iterator<Raton> iterator = ratonesOrdenados.iterator();
                        int i = 1;
                        System.out.println("Código Referencia, fecha nacimiento, peso, temperatura, sexo, cromosoma1, cromosoma2, Información adicional");
                        while (iterator.hasNext()){
                            System.out.println("Ratón "+i+": "+iterator.next().toCSV(","));
                            i++;
                        }
                        break;
                    }
                }
            }
            System.out.println("-----------------------------");
        }else{
            throw new DoNotExistException(ErrorType.PoblacionRatonesDoNotExist);
        }
    }

    /**
     * Crea una población de ratones pidiendo uno a uno los valores al usuario
     * @return Un objeto de tipo PoblacionRaton conteniendo los valores introducidos por teclado
     */
    public static PoblacionRatones crearPoblacion() throws IllegalArgument {
        String nombrePoblacion;
        String nombreResponsable;
        int diasProcreando;

        System.out.println("Nueva población: ");
        System.out.println("Introduzca el nombre de la población: ");
        nombrePoblacion = input();
        System.out.println("Introduzca el nombre del responsable: ");
        nombreResponsable = input();
        System.out.println("Introduzca el numero de días durante los cuales la población estará en las instalaciones procreando");
        diasProcreando = inputInt();

        return new PoblacionRatones(nombrePoblacion, nombreResponsable, diasProcreando);
    }

    /**
     * Crea y devuelve una población virtual, pidiendo al usuario los datos y porcentajes necesarios para crearla.
     * @return la población virtual.
     * @throws IllegalArgument si el número de días en la instalación no es válido. También si los porcentajes introducidos no son válidos.
     */
    public static PoblacionRatones crearPoblacionVirtual() throws IllegalArgument {
        String nombrePoblacion;
        String nombreResponsable;
        int diasProcreando; //menor de 270 días

        System.out.println("Nueva población Virtual: ");
        System.out.println("Introduzca el nombre de la población: ");
        nombrePoblacion = input();
        System.out.println("Introduzca el nombre del responsable: ");
        nombreResponsable = input();
        System.out.println("Introduzca el numero de días durante los cuales la población estará en las instalaciones procreando");
        diasProcreando = inputInt();
        System.out.println("Introduzca el número de ratones que quiere que tenga la población: ");
        int numRatones = inputInt();
        System.out.println("Introduzca el porcentaje de hembras: ");
        int pHembras = inputInt();
        System.out.println("Introduzca el porcentaje de machos estériles: ");
        int pMachosEst = inputInt();
        System.out.println("Introduzca el porcentaje de machos poligámicos: ");
        int pMachosPolig = inputInt();
        System.out.println("Introduzca el porcentaje de cromosomas X mutados en la población de hmebras: ");
        int pcXHembraMut = inputInt();

        return new PoblacionRatones(nombrePoblacion, nombreResponsable, diasProcreando, numRatones, pHembras, pMachosEst, pMachosPolig, pcXHembraMut);

    }

    /**
     * Crea un ratón a partir de los datos introducidos por pantalla.
     * @return un ratón con los valores introducidos por el usuario.
     * @throws DoNotExistException Si no hay poblaciones abiertas o si el nombre de la población especificado por teclado no existe
     * @throws IllegalArgument si el peso o la temperatura no son válidos
     */
    public static Raton nuevoRaton() throws DoNotExistException, IllegalArgument{
        //Código de referencia
        System.out.println("Introducir el codigo de referencia: ");
        int code = inputInt();

        //Fecha de nacimiento, sexo y cromosomas
        LocalDate nacimiento = inputNacimiento();
        Sexo sexo = inputSexo();
        System.out.println("Primer cromosoma: ");
        boolean mut1 = inputCromosoma();
        System.out.println("Segundo cromosoma: ");
        boolean mut2 = inputCromosoma();

        //Peso
        System.out.println("Introducir el peso en gramos");
        float peso = inputFloat();

        //Temperatura
        System.out.println("Introducir la temperatura corporal en Cº");
        float temp = inputFloat();

        //Campo texto
        System.out.println("Introduzca ahora la información adicional que quiera guardar");
        String campoTexto = input();

        return new Raton(code, nacimiento, peso, temp, sexo, mut1, mut2, campoTexto);
    }

    /**
     * Pide al usuario el código de referencia del ratón que quiere eliminar,
     * sacando previamente una lista de los códigos de todos los ratones de una población dada.
     * @param poblacion La población de la cual se quiera eliminar el ratón
     * @return el código de referencia del ratón
     * @throws DoNotExistException Si no hay poblaciones ya creadas, o si el ratón o población especificados por teclado no se encuentran
     */
    public static Integer inputCodeRatonAEliminar(PoblacionRatones poblacion) throws DoNotExistException{
            int numRef;
            if(poblacion.containsRatones()){
                Set<Integer> codes = poblacion.getCodesRatones();
                int i = 1;
                for(Integer code: codes){
                    System.out.println("Raton "+i+": "+code);
                    i++;
                }

                //Pedir y guardar el código de referencia del ratón que se quiere eliminar
                System.out.println("Introduzca el código de referencia del ratón que quiera eliminar");
                numRef = inputInt();
                return numRef;
            }else {
                throw new DoNotExistException(ErrorType.PoblacionDoesNotContainRatones);
            }

    }


    /**
     * Permite modificar los valores de uno de los atributos de un ratón: el peso, la temperatura o añadir información al campo de texto
     * @param poblacion la población de ratones de la cual se quiere modificar los valores de un ratón
     * @return un ratón con sus valores ya modificados
     * @throws DoNotExistException Si no hay poblaciones ya creadas, o si el ratón o población especificados por teclado no se encuentran
     * @throws IllegalArgument si el peso o la temperatura introducidos no son válidos
     */
    public static Raton modificarRaton(PoblacionRatones poblacion) throws DoNotExistException, IllegalArgument {
        int numRef;
        Set<Integer> codes = poblacion.getCodesRatones();
        int i = 1;
        for(Integer code: codes){
            System.out.println("Raton "+i+": "+code);
            i++;
        }
        System.out.println("Introduzca el numero de referencia del ratón que quiere modificar: ");
        numRef = inputInt();

        if(!poblacion.containsCodigoReferencia(numRef)){
            throw new DoNotExistException(ErrorType.RatonNotFound);
        }

        Raton ratonAModificar = poblacion.getRaton(numRef);

        int opcion = menuModificarRaton();
        while (opcion < 1 || opcion > 3) {
            System.out.println("Opción incorrecta, vuelva a introducir una opción: ");
            opcion = inputInt();
        }

        switch (opcion) {
            case 1 -> {
                System.out.println("Introducir el peso en gramos");
                float peso = inputFloat();
                ratonAModificar.setPeso(peso);

            }
            case 2 -> {
                System.out.println("Introducir la temperatura corporal en Cº");
                float temp = inputFloat();
                ratonAModificar.setTemperatura(temp);
            }
            case 3 -> {
                System.out.println("Introduzca ahora la información adicional que quiera guardar");
                ratonAModificar.setCampoTexto(input());
            }
            default -> System.out.println("Opción incorrecta");
        }
        return ratonAModificar;
    }


    /**
     * Saca por consola la información completa de un ratón, incluyendo la información sobre la población a la que pertenece
     * @param poblaciones  Un TreeMap con todas las poblaciones abiertas/creadas. La clave corresponde con el nombre de las poblaciones
     * @throws DoNotExistException Si no hay poblaciones ya creadas, o si el ratón o población especificados por teclado no se encuentran
     */
    public static void verInfoDetallada(TreeMap<String, PoblacionRatones> poblaciones) throws DoNotExistException{
        if(!poblaciones.isEmpty()){
            String nombrePoblacion = quePoblacion(poblaciones);
            if(poblaciones.containsKey(nombrePoblacion)){
                int numRef;
                PoblacionRatones poblacion = poblaciones.get(nombrePoblacion);

                //Sacar los códigos de los ratones por pantalla
                Set<Integer> codes = poblaciones.get(nombrePoblacion).getCodesRatones();
                int i = 1;
                for(Integer code: codes){
                    System.out.println("Raton "+i+": "+code);
                    i++;
                }

                System.out.println("Introduzca el numero de referencia del ratón que quiere ver: ");
                numRef = inputInt();

                try {
                    System.out.println("-------------------------------------------------------------");
                    System.out.println(poblacion);
                    System.out.println("\n"+poblacion.getRaton(numRef));
                    System.out.println("-------------------------------------------------------------");
                }catch (DoNotExistException doNotExist){
                    throw new DoNotExistException(ErrorType.RatonNotFound);
                }

            }else {
                throw new DoNotExistException(ErrorType.PoblacionRatonesDoNotExist);
            }
        }else{
            throw new DoNotExistException(ErrorType.PoblacionRatonesDoNotExist);
        }
    }


    /**
     * Realiza la simulación de Montecarlo sobre una población dada y saca por pantalla una
     * tabla con los porcentajes calculados para cada ciclo de reproducción.
     * @param poblacion la población sobre la que se hará la simulación
     * @throws DoNotExistException si la población no contiene ningún ratón
     * @throws IllegalArgument si se intenta introducir un macho como madre en una familia,
     * si el peso o la temperatura introducidos no son válidos,
     * o si uno de los ratones madre de una familia no es Hembra.
     */
    public static void printSimulacion(PoblacionRatones poblacion) throws DoNotExistException, IllegalArgument{
        if(!poblacion.containsRatones()){
            throw new DoNotExistException(ErrorType.PoblacionDoesNotContainRatones);
        }else{
            int[][] porcentajes = poblacion.simulacionMonteCarlo();
            System.out.println("Ciclo | nº Ratones | %H\t| %M\t| %Hn\t| %Hf\t| %He\t| %Mn\t| %Me\t| %Mp");
            System.out.println("------|-----------------------------------------------------------------");
            for(int[] fila: porcentajes){
                System.out.println(" "+fila[0]+"\t  | "+fila[1]+"\t\t   | "+fila[2]+"\t | "
                        +fila[3]+"\t| "+fila[4]+"\t| "+fila[5]+"\t| "+fila[6]+"\t| "+fila[7]+
                        "\t| "+fila[8]+"\t| "+fila[9]);

            }
        }
    }
    public static void main(String[] args) throws IllegalArgument {
        Consola consola = new Consola();

        //TreeMap<String,PoblacionRatones> poblaciones = new TreeMap<>();
        //PoblacionRatones p1 = consola.crearPoblacion();
        //poblaciones.put(p1.getNombrePoblacion(), p1);
        //System.out.println(poblaciones);

        //poblaciones = consola.nuevoRaton(poblaciones);

        PoblacionRatones p1 = consola.crearPoblacionVirtual();
        Consola.printSimulacion(p1);
    }
}
