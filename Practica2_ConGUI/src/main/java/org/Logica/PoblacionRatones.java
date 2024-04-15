package org.Logica;

import org.Excepciones.DoNotExistException;
import org.Excepciones.ErrorType;
import org.Excepciones.IllegalArgument;

import java.time.LocalDate;
import java.util.*;

/**
 * Representa una población de ratones
 * @author Mamen Cortés Navarro
 */
public class PoblacionRatones {
    //Atributos de la clase
    private String nombrePoblacion;
    private String nombreResponsable;
    private int diasEnInstalacion;
    private static final int maxDiasInstalacion =630;
    private static final int diasMultiploDe = 5;
    /**
     * TreeMap que almacenará los ratones de la población.
     * <p>La clave del mapa será el código de referencia</p>
     */
    private TreeMap<Integer, Raton> ratonTreeMap; //Donde la clave será el código de referencia

    /**
     * Constructor de la clase
     * @param nombrePoblacion El nombre de la población
     * @param nombreResponsable El nombre del responsable
     * @param diasEnInstalacion El número de días que estará la población en las instalaciones. (<630 y múltiplo de 5)
     * @throws IllegalArgument si el número de días en la instalación no está entre 0 y 630, o si no es múltiplo de 5
     */
    public PoblacionRatones(String nombrePoblacion, String nombreResponsable, int diasEnInstalacion) throws IllegalArgument{
        this.nombrePoblacion = nombrePoblacion;
        this.nombreResponsable = nombreResponsable;
        //Los días en la instalación deben ser un múltiplo de 5 y menor que 630
        if(diasEnInstalacion>maxDiasInstalacion||diasEnInstalacion<0||diasEnInstalacion%diasMultiploDe!=0){
            throw new IllegalArgument("Los días en la instalación introducidos no son válidos. Debe se un valor entre 0-"+maxDiasInstalacion+" y ser múltiplo de"+diasMultiploDe, ErrorType.IllegalDiasEnInstalacion);
        }else {
            this.diasEnInstalacion = diasEnInstalacion;
        }

        this.ratonTreeMap = new TreeMap<>();
    }

    /**
     * Constructor de una población virtual de ratones.
     * <p>Atendiendo a los parámetros introducidos, se crea una población aleatoria de ratones.</p>
     * @param nombrePoblacion el nombre de la población
     * @param nombreResponsable el nombre del responsable
     * @param diasEnInstalacion El número de días que estará la población en las instalaciones. (<630 y múltiplo de 5)
     * @param NR Número de ratones de la población
     * @param pH Porcentaje de hembras. Entre 0 y 100.
     * @param pMe Porcentaje de machos estériles. Entre 0 y 100.
     * @param pMp Porcentaje de machos poligámicos. Entre 0 y 100.
     * @param pcXHmut Porcentaje de cromosomas X estériles entre la población de hembras. Entre 0 y 100.
     * @throws IllegalArgument Si el número de días en la instalación no es válido. También si los porcentajes introducidos no son válidos.
     */
    public PoblacionRatones(String nombrePoblacion, String nombreResponsable, int diasEnInstalacion,
                            int NR, int pH, int pMe, int pMp, int pcXHmut) throws IllegalArgument {
        this(nombrePoblacion, nombreResponsable, diasEnInstalacion);
        //Pruebas de validación
        if(pH<0||pH>100)throw new IllegalArgument("El porcentaje de Hembras debe estar entre 0 y 100",ErrorType.IllegalPercentage);
        if(pMe<0||pMe>100)throw new IllegalArgument("El porcentaje de Machos estériles debe estar entre 0 y 100",ErrorType.IllegalPercentage);
        if(pMp<0||pMp>100)throw new IllegalArgument("El porcentaje de Machos poligámicos debe estar entre 0 y 100",ErrorType.IllegalPercentage);
        if(pcXHmut<0||pcXHmut>100)throw new IllegalArgument("El porcentaje de cromosomas X estériles en la población de Hembras debe estar entre 0 y 100",ErrorType.IllegalPercentage);

        int pM = 100-pH; //porcentaje de machos

        //crear una lista de tipo Pila con NR números
        //Estos valores serán los códigos de referencia de los ratones
        Stack<Integer> codes = new Stack<>();
        for(int i = 1; i<(1+NR);i++){
            codes.add(i);
        }

        //creación de la población virtual
        for(int i = 0; i<NR; i++){
            int randomNum = (int) (Math.random()*100);
            if(randomNum<pM){
                //El ratón será un macho
                int randomNum2 = (int) (Math.random()*100);
                int randomNum3 = (int) (Math.random()*100);
                if(randomNum2<pMe&&randomNum3<pMp){
                    //el ratón será estéril y poligámico
                    //Cromosoma x e y mutado
                    int code = codes.pop();
                    Raton r1 = new Raton(code, LocalDate.now(), 15f, 35f, Sexo.MACHO, true, true, "Ratón estéril y poligámico");
                    ratonTreeMap.put(code,r1 );
                }else if(randomNum2<pMe){
                    //El ratón será estéril
                    //cromosoma x mutado
                    int code = codes.pop();
                    Raton r1 = new Raton(code, LocalDate.now(), 15f, 35f, Sexo.MACHO, true, false, "Ratón estéril");
                    ratonTreeMap.put(code,r1 );
                }else if(randomNum3<pMp){
                    //El ratón será poligámico
                    //cromosoma y mutado
                    int code = codes.pop();
                    Raton r1 = new Raton(code, LocalDate.now(), 15f, 35f, Sexo.MACHO, false, true, "Ratón poligámico");
                    ratonTreeMap.put(code,r1 );
                }else {
                    //el ratón no contendrá mutaciones
                    int code = codes.pop();
                    Raton r1 = new Raton(code, LocalDate.now(), 15f, 35f, Sexo.MACHO, false, false, "Ratón sin patologías");
                    ratonTreeMap.put(code,r1 );
                }

            }else{
                //El ratón será hembra
                int randomNum2 = (int) (Math.random()*100);
                int randomNum3 = (int) (Math.random()*100);
                if(randomNum2<pcXHmut&&randomNum3<pcXHmut){
                    //La hembra será estéril
                    //Ambos cromosomas X mutados
                    int code = codes.pop();
                    Raton r1 = new Raton(code, LocalDate.now(), 15f, 35f, Sexo.HEMBRA, true, true, "Ratón estéril");
                    ratonTreeMap.put(code,r1 );
                }else if(randomNum2<pcXHmut){
                    //primer cromosoma x mutado
                    int code = codes.pop();
                    Raton r1 = new Raton(code, LocalDate.now(), 15f, 35f, Sexo.HEMBRA, true, false, "Ratón sin patologías");
                    ratonTreeMap.put(code,r1 );
                }else if(randomNum3<pcXHmut){
                    //segundo cromosoma x mutado
                    int code = codes.pop();
                    Raton r1 = new Raton(code, LocalDate.now(), 15f, 35f, Sexo.HEMBRA, false, true, "Ratón sin patologías");
                    ratonTreeMap.put(code,r1 );
                }else {
                    //el ratón no contendrá mutaciones
                    int code = codes.pop();
                    Raton r1 = new Raton(code, LocalDate.now(), 15f, 35f, Sexo.HEMBRA, false, false, "Ratón sin patologías");
                    ratonTreeMap.put(code,r1 );
                }
            }
        }
    }

    /**
     * Realiza la simulación de Montecarlo sobre esta población de ratones.
     * <p>
     *     Para ello, mientras el número de días que llevan procreando sea menor que this.diasEnInstalacion,
     *     creará 3 tipos de familias con los ratones maduros. El tipo dependerá de las patologías del macho.
     *     <ul>
     *     <li>Macho sin mutaciones: Familia Normal</li>
     *     <li>Macho poligámico (Cromosoma Ymut): Familia poligámica</li>
     *     <li>Macho estéril (Cromosoma Xmut): Familia Macho Estéril (que podrá ser poligámica o no)</li>
     *     </ul>
     * </p>
     * <p>
     *     Tras ello, las familias procrean y los hijos se almacenan en una lista a parte (ratonesInmaduros).
     *     Los ratones serán maduros después de 2 ciclos de reproducción. Entonces, se incluirán en esta población
     *     y formarán sus propias familias.
     * </p>
     * <p>
     *      En cada ciclo de reproducción se calculan los porcentajes de hembras y machos sobre la población total.
     *      También, el porcentaje de hembras sin cromosomas mutados (normales), de hembras con un solo
     *      cromosoma mutado (fértiles) y de hembras con ambos cromosomas mutados (estériles).
     *      Por último, calcula el porcentaje de machos sin cromosomas mutados (normales), de machos con el cromosoma x
     *      mutado (estériles) y aquellos con el cromosoma y mutado (propensos a la poligamia).
     *      Un macho puede ser estéril y polígamo al mismo tiempo.
     * </p>
     *
     * <p>
     *    El array que se calcula en cada ciclo sigue el siguiente orden:
     *    <ul>
     *    <li>El ciclo de reproducción</li>
     *    <li>El número total de ratones</li>
     *    <li>El porcentaje de hembras, sobre la población total </li>
     *    <li>El porcentaje de machos, sobre la población total</li>
     *    <li>El porcentaje de hembras normales, sobre la población de hembras</li>
     *    <li>El porcentaje de hembras fértiles, sobre la población de hembras</li>
     *    <li>El porcentaje de hembras estériles, sobre la población de hembras</li>
     *    <li>El porcentaje de machos normales, sobre la población de machos</li>
     *    <li>El porcentaje de machos estériles, sobre la población de machos</li>
     *    <li>El porcentaje de machos poligámicos, sobre la población de machos</li>
     * </p>
     *</ul>
     * @return un array de dos dimensiones con los porcentajes calculados en cada ciclo.
     * @throws IllegalArgument si se intenta introducir un macho como madre en una familia,
     * si la temperatura o el peso de alguno de los ratones hijos no es válido,
     * o si uno de los ratones madre no es Hembra.
     */
    public int[][] simulacionMonteCarlo() throws IllegalArgument {

        TreeMap<Integer, Raton> ratonesInmaduros = new TreeMap<>();
        int diasProcreando = 0;

        int[][] porcentajes = new int[(getDiasEnInstalacion()/45)+1][10];

        porcentajes[0] = calculatePercentages(ratonesInmaduros, 0);

        //Durante maxDiasInstalación días los ratones estarán reproduciéndose
        while (diasProcreando<getDiasEnInstalacion()) {

            Stack<Raton> hembras = new Stack<>();
            Stack<Raton> machos = new Stack<>();

            //Dividir los ratones en dos pilas según su sexo
            Iterator<Raton> iterador = ratonTreeMap.values().iterator();
            while (iterador.hasNext()) {
                Raton raton = iterador.next();
                if (raton.getSexo().equals(Sexo.HEMBRA)) {
                    hembras.add(raton);
                } else if (raton.getSexo().equals(Sexo.MACHO)) {
                    machos.add(raton);
                }
            }

            LinkedList<Familia> familias = new LinkedList<>();

            //Creación de familias según los cromosomas del macho
            while (!hembras.isEmpty() && !machos.isEmpty()) {
                Raton macho = machos.pop();
                if (macho.getCromosoma1().equals(Cromosoma.Xmut) && macho.getCromosoma2().equals(Cromosoma.Y)) {
                    //El ratón es estéril no poligámico
                    familias.add(new FamiliaMachoEsteril(macho, hembras.pop()));
                } else if (macho.getCromosoma1().equals(Cromosoma.Xmut) && macho.getCromosoma2().equals(Cromosoma.Ymut)) {
                    //El macho es estéril poligámico
                    FamiliaMachoEsteril familia = new FamiliaMachoEsteril(macho, hembras.pop());
                    int randomNum = (int) (Math.random() * 10);
                    while (randomNum >= 5 && !hembras.isEmpty()) {
                        familia.addMadre(hembras.pop());
                        randomNum = (int) (Math.random() * 10);
                    }
                    familias.add(familia);
                } else if (macho.getCromosoma2().equals(Cromosoma.Ymut)) {
                    //Familia poligámica
                    FamiliaPoligamica familia = new FamiliaPoligamica(macho, hembras.pop());
                    int randomNum = (int) (Math.random() * 10);
                    while (randomNum >= 5 && !hembras.isEmpty()) {
                        familia.addMadre(hembras.pop());
                        randomNum = (int) (Math.random() * 10);
                    }
                    familias.add(familia);

                } else {
                    //El macho no es estéril ni poligámico.
                    //Familia normal
                    familias.add(new FamiliaNormal(macho, hembras.pop()));
                }
            }

            //Añadir a la población los ratones del ciclo anterior para que se reproduzcan en el siguiente
            //Vaciar la lista de ratones inmaduros para rellenarla con los hijos creados en este ciclo
            ratonTreeMap.putAll(ratonesInmaduros);
            ratonesInmaduros.clear();

            //Comienza la procreación
            for (Familia familia: familias) {
                if(ratonesInmaduros.isEmpty()){
                    familia.procrear(getMaxCode());
                }else {
                    familia.procrear(getMaxCode()+ratonesInmaduros.lastKey());
                }
                //los hijos de este ciclo se añaden a la lista de ratones inmaduros
                ratonesInmaduros.putAll(familia.getHijos());
            }

            //Cálculo de porcentajes
            porcentajes[(diasProcreando/45)] = calculatePercentages(ratonesInmaduros, (diasProcreando/45)+1);
            diasProcreando += 45;
        }
        ratonTreeMap.putAll(ratonesInmaduros);
        return porcentajes;
    }
    private int[] calculatePercentages(TreeMap<Integer, Raton> ratonesInmaduros, int ciclo){
        //h = hembra, m = macho
        //e = estéril, fe = fértil pero un cromosoma estéril, p = poligámico, n = normal
        int h = 0, m = 0, he = 0, hn = 0, hfe = 0, me = 0, mp = 0, mn = 0;

        for(Raton raton:ratonTreeMap.values()){
            if(raton.getSexo().equals(Sexo.HEMBRA)){
                h++;
                if(raton.getCromosoma1().equals(Cromosoma.X)&&raton.getCromosoma2().equals(Cromosoma.X)){
                    hn++;
                } else if (raton.getCromosoma1().equals(Cromosoma.Xmut)&&raton.getCromosoma2().equals(Cromosoma.Xmut)) {
                    he++;
                }else{
                    hfe++;
                }
            } else {
                m++;
                if(raton.getCromosoma1().equals(Cromosoma.Xmut)&&raton.getCromosoma2().equals(Cromosoma.Ymut)){
                    me++;
                    mp++;
                }else if(raton.getCromosoma1().equals(Cromosoma.Xmut)){
                    me++;
                }else if (raton.getCromosoma2().equals(Cromosoma.Ymut)) {
                    mp++;
                }else{
                    mn++;
                }
            }
        }
        for(Raton raton:ratonesInmaduros.values()){
            if(raton.getSexo().equals(Sexo.HEMBRA)){
                h++;
                if(raton.getCromosoma1().equals(Cromosoma.X)&&raton.getCromosoma2().equals(Cromosoma.X)){
                    hn++;
                } else if (raton.getCromosoma1().equals(Cromosoma.Xmut)&&raton.getCromosoma2().equals(Cromosoma.Xmut)) {
                    he++;
                }else{
                    hfe++;
                }
            } else {
                m++;
                if(raton.getCromosoma1().equals(Cromosoma.Xmut)&&raton.getCromosoma2().equals(Cromosoma.Ymut)){
                    me++;
                    mp++;
                }else if(raton.getCromosoma1().equals(Cromosoma.Xmut)){
                    me++;
                }else if (raton.getCromosoma2().equals(Cromosoma.Ymut)) {
                    mp++;
                }else{
                    mn++;
                }
            }
        }
        int numRat = ratonTreeMap.size()+ratonesInmaduros.size();
        int porcentajeH = h*100/numRat;

        return new int[]{ciclo, numRat, porcentajeH, 100-porcentajeH, (hn*100/h), (hfe*100/h), (he*100/h), (mn*100/m), (me*100/m), (mp*100/m)};
    }

    //Getters

    /**
     * Devuelve el código máximo de los ratones de la población
     * @return el código máximo
     */
    public int getMaxCode(){
        return ratonTreeMap.lastKey();
    }

    /**
     * Devuelve el nombre de la población
     * @return el nombre de la población
     */

    public String getNombrePoblacion() {
        return nombrePoblacion;
    }

    /**
     * Devuelve el nombre del responsable
     * @return el nombre del responsable
     */
    public String getNombreResponsable() {
        return nombreResponsable;
    }

    /**
     * Devuelve los días que estará la población en la instalación
     * @return  el número de días que los ratones estarán en las instalaciones
     */
    public int getDiasEnInstalacion() {
        return diasEnInstalacion;
    }

    /**
     * Devuelve un conjunto con los códigos de referencia de los ratones de la población
     * @return la lista
     */
    public Set<Integer> getCodesRatones(){
        return ratonTreeMap.keySet();
    }

    /**
     * Comprueba si alguno de los ratones de la población contiene el código de referencia especificado
     * @param codigoRef el codigo de referencia
     * @return true si existe un ratón con este código, false si no.
     */
    public boolean containsCodigoReferencia(int codigoRef){
        return ratonTreeMap.containsKey(codigoRef);
    }

    /**
     * Comprueba si hay ratones guardados en la población
     * @return true si la población contiene al menos un ratón, false si está vacía.
     */
    public boolean containsRatones(){
        return !ratonTreeMap.isEmpty();
    }
    /**
     * Devuelve una copia de los ratones de la población ordenados por su código de referencia.
     * @return un TreeSet con los valores ordenados
     */
    public TreeSet<Raton> getRatonesOrdenadosPorCodigo(){
        TreeSet<Raton> ratonesOrdenados = new TreeSet<>(ratonTreeMap.values());
        return ratonesOrdenados;
    }
    /**
     * Devuelve una copia de los ratones de la población ordenados por su fecha de nacimiento.
     * @return un TreeSet con los valores ordenados
     */
    public TreeSet<Raton> getRatonesOrdenadosPorNacimiento(){
        TreeSet<Raton> ratonesOrdenados = new TreeSet<>(new ComparatorRatonByNacimiento());
        ratonesOrdenados.addAll(ratonTreeMap.values());
        return ratonesOrdenados;
    }

    /**
     * Devuelve una copia de los ratones de la población ordenados por su peso, de mayor a menor.
     * @return un TreeSet con los valores ordenados
     */
    public TreeSet<Raton> getRatonesOrdenadosPorPesoDescendente(){
        TreeSet<Raton> ratonesOrdenados = new TreeSet<>(new ComparatorRatonPesoDescendente());
        ratonesOrdenados.addAll(ratonTreeMap.values());
        return ratonesOrdenados;
    }

    /**
     * Añade un nuevo ratón a la población
     * @param raton El ratón que se añadirá
     * @throws IllegalArgument si ya existe un ratón con el mismo código de referencia.
     */
    public void addRaton(Raton raton) throws IllegalArgument {
        if(ratonTreeMap.containsKey(raton.getCodigoReferencia())) {
            throw new IllegalArgument("Ya existe un ratón con el mismo código de referencia", ErrorType.RatonAlreadyExistsWithThisID);
        }
        this.ratonTreeMap.put(raton.getCodigoReferencia(), raton);
    }

    /**
     * Eliminar un ratón de la población según su código de referencia
     * @param codigoReferencia El código de referencia del ratón
     * @throws DoNotExistException Si no se encuentra el ratón con dicho código de referencia
     */
    public void deleteRaton(int codigoReferencia) throws DoNotExistException{

        if(ratonTreeMap.remove(codigoReferencia) == null){
            throw new DoNotExistException(ErrorType.RatonNotFound);
        }else {
            System.out.println("Ratón eliminado");
        }
    }

    /**
     * Busca un ratón dentro de la lista de ratones, habiendo indicado antes su código de referencia.
     * @param codigoReferencia El código de referencia del ratón que se busca
     * @return Devuelve dicho ratón
     * @throws DoNotExistException Si no se encuentra el ratón con dicho código de referencia
     */
    public Raton getRaton(int codigoReferencia) throws DoNotExistException{
        Raton targetRaton = ratonTreeMap.get(codigoReferencia);
        if(targetRaton == null){
            throw new DoNotExistException(ErrorType.RatonNotFound);
        }else {
            return targetRaton;
        }
    }

    /**
     * Sobreescribe el método toString para devolver un String conteniendo los datos de la población
     * No devuelve información sobre sus ratones
     * @return Un String con la información de la población
     */
    @Override
    public String toString() {
        return "Información de la población:" +
                "\nNombre de la población: "+getNombrePoblacion()+
                "\nNombre del responsable: "+getNombreResponsable()+
                "\nTiempo de estancia en la instalación: "+ getDiasEnInstalacion();
    }


    /**
     * Cambia los valores del peso, temperatura y campo de texto de un ratón de la población por
     * los del ratón especificado. Para ello, ambos deben tener el mismo código de referencia.
     * @param raton el ratón modificado
     * @throws IllegalArgument si los valores del peso o temperatura del nuevo ratón no son válidos;
     *          si la población no contiene un Raton con el mismo código que raton
     */
    //Método que cambie los valores de un ratón
    public void modificarRaton(Raton raton) throws IllegalArgument {
        if(!ratonTreeMap.containsKey(raton.getCodigoReferencia())){
            throw new DoNotExistException(ErrorType.RatonNotFound);
        }else {
            ratonTreeMap.get(raton.getCodigoReferencia()).setPeso(raton.getPeso());
            ratonTreeMap.get(raton.getCodigoReferencia()).setTemperatura(raton.getTemperatura());
            ratonTreeMap.get(raton.getCodigoReferencia()).setCampoTexto(raton.getCampoTexto());
        }
    }

    /**
     * Se utiliza para codificar el contenido de la población utilizando el formato CSV (coma separated values).
     * @param separador el caracter con el que marcará la separación de los valores, que puede ser una coma o no
     * @return el contenido codificado en formato CSV
     */
    public String toCSV(String separador){
        String toCSV = nombrePoblacion+separador+nombreResponsable+separador+diasEnInstalacion;
        Iterator<Raton> iterator = ratonTreeMap.values().iterator();
        while (iterator.hasNext()){
            toCSV = toCSV+"\n"+iterator.next().toCSV(separador);
        }
        return toCSV;
    }
    /**
     * Crea una población de ratones a partir de una cadena de caracteres en formato CSV
     * @param fromCSV la cadena de caracteres en fromato CSV
     * @param separador el caracter que marca la separación de valores
     * @return una población de ratones
     * @throws IllegalArgument si el peso, temperatura o cromosomas no son válidos; si el número de días en la instalación no está entre 0 y 630, o si no es múltiplo de 5; si ya existe un ratón con el mismo código de referencia.
     */
    public static PoblacionRatones fromCSV(String fromCSV, String separador) throws IllegalArgument{
        try {
            String[] atributos = fromCSV.split("\n");
            String[] valoresPoblacion= atributos[0].split(separador);
            String nombrePoblacion = valoresPoblacion[0];
            String nombreResponsable = valoresPoblacion[1];
            int diasEnInstalacion = Integer.parseInt(valoresPoblacion[2]);

            PoblacionRatones p1 = new PoblacionRatones(nombrePoblacion, nombreResponsable, diasEnInstalacion);

            for(int i=1; i<atributos.length; i++){
                p1.addRaton(Raton.fromCSV(atributos[i], ","));
            }
            return p1;

        }catch (IndexOutOfBoundsException | NumberFormatException iOoB){
            throw new IllegalArgument("Illegal CSV", ErrorType.IllegalCSV);
        }
    }
    /**
     * Comprueba si esta población es igual a la población especificada.
     * Dos poblaciones serán iguales si tienen el mismo nombre.
     * @param obj el objeto que se quiere comparar
     * @return true si son iguales, false si no lo son.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj!=null){
            if (obj instanceof PoblacionRatones){
                PoblacionRatones poblacionRatones = (PoblacionRatones) obj;
                return this.getNombrePoblacion().equals(poblacionRatones.getNombrePoblacion()) ;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            /*PoblacionRatones p1 = new PoblacionRatones("poblacion1", "Mamen Cortés", 350);
            p1.addRaton(new Raton(123, LocalDate.now(), 12.5f, 35f, Sexo.HEMBRA, true, false, ""));
            p1.addRaton(new Raton(124, LocalDate.now(), 12.5f, 35f, Sexo.HEMBRA, true, false, ""));
            p1.addRaton(new Raton(125, LocalDate.now(), 12.5f, 35f, Sexo.HEMBRA, true, false, ""));
            p1.modificarPesoRaton(50f, 123);
            String fromCSV = p1.toCSV(",");
            System.out.println("To CSV:");
            System.out.println(fromCSV);
            PoblacionRatones p2 = PoblacionRatones.fromCSV(fromCSV, ",");
            System.out.println("From CSV:");
            System.out.println(p2);
            System.out.println(p2.getRatonesMap());

            System.out.println(p1.getRatonesPorCodigo());*/

            PoblacionRatones simulacion = new PoblacionRatones("simulacion", "Mamen cortés", 95, 30, 40, 10, 40, 70);
            //System.out.println(simulacion.toCSV(","));
            simulacion.simulacionMonteCarlo();

        }catch (IllegalArgument iAex){
            System.out.println(iAex.getErrorType());
        }

    }
    public static void prueba(Familia familia){
        System.out.println(familia);
    }

}