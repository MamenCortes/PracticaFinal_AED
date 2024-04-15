package org.Logica;

import org.Excepciones.ErrorType;
import org.Excepciones.IllegalArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TreeSet;

/**
 * Representa un ratón
 * @author Mamen Cortés Navarro
 */
public class Raton implements Comparable<Raton>{
    //Atributos de la clase Raton
    private final int codigoReferencia;
    private final LocalDate nacimiento;
    private float peso;
    private float temperatura;
    private String campoTexto;
    private final Sexo sexo;
    private final Cromosoma[] cromosomas = new Cromosoma[2];

    /**
     * Constructor de la clase
     * @param codigoReferencia El código de referencia del Ratón. Definirá su identidad.
     * @param nacimiento La fecha de nacimiento
     * @param peso El peso en gramos. Debe estar entre 0-50 g para ser válido.
     * @param temperatura La temperatura en grados centígrados. Debe estar entre 20-40º para ser válida.
     * @param sexo El sexo
     * @param mut1 Boolean que indica si el primer cromosoma contendrá o no una mutación
     * @param mut2 Boolean que indica si el primer cromosoma contendrá o no una mutación
     * @param campoTexto Cualquier información adicional sobre el ratón
     * @throws IllegalArgument Si el peso o la temperatura no son válidos
     */
    public Raton(int codigoReferencia, LocalDate nacimiento,
                 float peso, float temperatura, Sexo sexo, boolean mut1, boolean mut2, String campoTexto) throws IllegalArgument{
        this.codigoReferencia = codigoReferencia;
        this.nacimiento = nacimiento;
        if(pesoValido(peso)){this.peso = peso;}
        if(temperaturaValida(temperatura)){this.temperatura = temperatura;}
        this.campoTexto = campoTexto;
        this.sexo = sexo;
        if (this.sexo == Sexo.HEMBRA){ //Si es hembra, tendrá dos cromosomas X
            cromosomas[0] = getTipoCromosoma("x", mut1);
            cromosomas[1] = getTipoCromosoma("x", mut2);

        }else if(this.sexo == Sexo.MACHO){ //Si es macho, tendrá un cromosoma Y y otro X
            cromosomas[0] = getTipoCromosoma("x", mut1);
            cromosomas[1] = getTipoCromosoma("y", mut2);
        }
    }

    /**
     * @param codigoReferencia El código de referencia del Ratón. Definirá su identidad.
     * @param nacimiento La fecha de nacimiento
     * @param peso El peso en gramos. Debe estar entre 0-50 g para ser válido.
     * @param temperatura La temperatura en grados centígrados. Debe estar entre 20-40º para ser válida.
     * @param sexo El sexo
     * @param cr1 El primer cromosoma
     * @param cr2 El segundo cromosoma
     * @param campoTexto Información adicional
     * @throws IllegalArgument si la temperatura o el peso no son válidos.
     *                También si los cromosomas introducidos no concuerdan con el sexo del ratón.
     *                Los ratones macho deben tener un cromosoma X y otro Y, y las hembras 2 cromosomas X.
     */
    public Raton(int codigoReferencia, LocalDate nacimiento, float peso, float temperatura,
                 Sexo sexo, Cromosoma cr1, Cromosoma cr2, String campoTexto) throws IllegalArgument{
        this.codigoReferencia = codigoReferencia;
        this.nacimiento = nacimiento;
        if(pesoValido(peso)){this.peso = peso; };
        if(temperaturaValida(temperatura)){this.temperatura = temperatura;}
        this.campoTexto = campoTexto;
        this.sexo = sexo;
        if(cromosomasValidos(sexo, cr1, cr2)){
            cromosomas[0] = cr1;
            cromosomas[1] = cr2;
        }else {
            throw new IllegalArgument("Los cromosomas introducidos no corresponden con el sexo", ErrorType.IllegalCromosomas);
        }
    }

    /**
     * Indica si el peso dado es válido.
     * Debe estar entre 0 y 50 g para ser válido
     * @param peso el peso a validar
     * @return true si es válido
     * @throws IllegalArgument si el peso no es válido
     */
    private boolean pesoValido(float peso) throws IllegalArgument{
        if(peso<0||peso>50){
            throw new IllegalArgument("El peso introducido no es válido. Debe estar entre 0 y 50 gramos",ErrorType.IllegalPeso);
        }else {
            return true;
        }
    }
    /**
     * Indica si la temperatura dada es válido.
     * Debe estar entre 20º y 40º para ser válido
     * @param temp la temperatura a validar
     * @return true si es válida
     * @throws IllegalArgument si no es válida
     */
    private boolean temperaturaValida(float temp) throws IllegalArgument{
        if(temp<20f||temp>40f){
            throw new IllegalArgument("La temperatura introducida no es válida, debe estar entre 20º y 40º",ErrorType.IllegalTemperatura);
        }else {
            return true;
        }

    }

    /**
     * Crea un Cromosoma.
     * @param tipo el tipo de cromosoma (x o y)
     * @param mut si el cromosoma estará mutado o no
     * @return un Cromosoma del tipo y mutación especificado
     * @throws IllegalArgument si el tipo introducido no es ni x ni y
     */
    private Cromosoma getTipoCromosoma(String tipo, boolean mut) throws IllegalArgument{
        if (tipo.equals("x")){ //Si es hembra, tendrá dos cromosomas X
            if(mut){
                return Cromosoma.Xmut;
            }else {
                return Cromosoma.X;
            }

        }else if(tipo.equals("y")){ //Si es macho, tendrá un cromosoma Y y otro X
            if(mut){
                return Cromosoma.Ymut;
            }else {
                return Cromosoma.Y;
            }
        }else {
            throw new IllegalArgument("El tipo de cromosoma debe ser x o y", ErrorType.IllegalCromosomas);
        }
    }

    /**
     * Indica si los cromosomas dados corresponden con el sexo.
     * Las hembras tienen 2 cromosomas X. Los machos un cromosoma X y otro Y.
     * @param sexo el sexo del ratón
     * @param c1 el primer cromosoma
     * @param c2 el segundo cromosoma
     * @return true si son válidos, false si no lo son
     */
    private boolean cromosomasValidos(Sexo sexo, Cromosoma c1, Cromosoma c2){
        if (sexo.equals(Sexo.HEMBRA)){
            return (c1 == Cromosoma.X || c1 == Cromosoma.Xmut) && (c2 == Cromosoma.X || c2 == Cromosoma.Xmut);
        }else {
            return (c1 == Cromosoma.X || c1 == Cromosoma.Xmut) && (c2 == Cromosoma.Y || c2 == Cromosoma.Ymut);
        }
    }
    //Getters y Setters

    /**
     * Devuelve el código de referencia
     * @return el código de referencia
     */
    public int getCodigoReferencia() {
        return codigoReferencia;
    }

    /**
     * Devuelve la variable nacimiento
     * @return la fecha de nacimiento
     */
    public LocalDate getNacimiento() {
        return nacimiento;
    }

    /**
     * Devuelve el peso en gramos
     * @return el peso en gramos
     */
    public float getPeso() {
        return peso;
    }

    /**
     * Modifica el peso
     * @param peso El nuevo peso en gramos
     * @throws IllegalArgument si el peso no es válido
     */
    public void setPeso(float peso) throws IllegalArgument{
        if(pesoValido(peso))this.peso = peso;
    }

    /**
     * Devuelve la temperatura en grados Cº
     * @return la temperatura
     */
    public float getTemperatura() {
        return temperatura;
    }

    /**
     * Modifica la temperatura
     * @param temperatura la nueva temperatura en grados celsius
     * @throws IllegalArgument si la temperatura no es válida
     */
    public void setTemperatura(float temperatura) throws IllegalArgument{
        if(temperaturaValida(temperatura))this.temperatura = temperatura;
    }

    /**
     * Devuelve la información adicional del ratón
     * @return la información adicional
     */
    public String getCampoTexto() {
        return campoTexto;
    }

    /**
     * Añade información adicional al ratón
     * @param campoTexto La nueva información que se quiera añadir al campo de texto
     */
    public void setCampoTexto(String campoTexto) {
        this.campoTexto += campoTexto;
    }
    /**
     * Devuelve el sexo
     * @return el sexo del ratón
     */
    public Sexo getSexo() {
        return sexo;
    }

    /**
     * Devuelve el primer cromosoma
     * @return el primer cromosoma
     */
    public Cromosoma getCromosoma1(){return cromosomas[0]; }
    /**
     * Devuelve el segundo cromosoma
     * @return el segundo cromosoma
     */
    public Cromosoma getCromosoma2(){return cromosomas[1]; }

    /**
     * Sobreescribe el método toString para devolver un String todos los datos del ratón y sus patologías
     * <p>
     *     Si el cromosoma X del ratón contiene una mutación, este será estéril.
     *     Para que una hembra sea estéril ambos cromosomas X deben contener la mutación.
     *     Si el cromosoma Y del ratón macho contiene una mutación, este será propenso a la poligamia.
     * </p>
     *
     * @return Un string con toda la información del ratón
     */
    @Override
    public String toString() {
        String cromosomasToString = "";
        for (Cromosoma crom: cromosomas){
            if(crom== Cromosoma.Y ) {
                cromosomasToString += "Cromosoma Y sin mutacion ";
            }else if (crom == Cromosoma.Ymut){
                cromosomasToString += "Cromosoma Y mutado ";
            } else if (crom == Cromosoma.X) {
                cromosomasToString += "Cromosoma X sin mutacion ";
            }else{
                cromosomasToString += "Cromosoma X mutado ";
            }
        }

        String patologia = "";
        if(this.sexo == Sexo.HEMBRA){
            if (cromosomas[0].equals(Cromosoma.Xmut) && cromosomas[1].equals(Cromosoma.Xmut)){
                patologia = "La hembra es estéril.";
            }else {
                patologia = "La hembra no tiene ninguna patología";
            }


        } else {
            int numPatologias = 0;
            if(cromosomas[1].equals(Cromosoma.Ymut)){
                patologia = "El macho es propenso a la poligamia. ";
                numPatologias++;
            }
            if (cromosomas[0].equals(Cromosoma.Xmut)){
                patologia = patologia.concat("El macho es estéril");
                numPatologias++;
            }
            if(numPatologias==0){
                patologia = "El macho no tiene ninguna patología";
            }

        }

        return "Información del ratón:" +
                "\nCódigo de referencia: "+getCodigoReferencia()+
                "\nFecha de nacimiento: "+getNacimiento()+
                "\nSexo: "+getSexo()+
                "\nPeso: "+getPeso()+
                "\nTemperatura: "+getTemperatura()+
                "\nCromosomas: "+cromosomasToString+
                "\n"+patologia+
                "\nInformación adicional: "+getCampoTexto();
    }

    /**
     * Se utiliza para codificar el contenido del ratón utilizando el formato CSV (coma separated values).
     * @param separador el caracter con el que se quieren separar los valores, que puede ser una coma o no
     * @return el contenido codificado en formato CSV
     */
    public String toCSV(String separador){
        String text = campoTexto;
        if(this.campoTexto.equals("")){
            text = "null";
        }
        //Faltan los cromosomas
        return codigoReferencia+separador+nacimiento+separador+peso+separador+temperatura+separador
                +sexo.toString()+separador+cromosomas[0]+separador+cromosomas[1]+separador+text;
    }

    /**
     * Crea un ratón a partir de una cadena de caracteres codificada en formato CSV
     * @param fromCSV la cadena de caracteres en fromato CSV
     * @param separador el caracter que determina la separación de valores
     * @return un ratón
     * @throws IllegalArgument si el peso, temperatura o cromosomas no son válidos
     */
    public static Raton fromCSV(String fromCSV, String separador) throws IllegalArgument {
        try {
            String[] atributos = fromCSV.split(separador);
            int codigoRef = Integer.parseInt(atributos[0]);
            LocalDate nacimiento=LocalDate.parse(atributos[1], DateTimeFormatter.ISO_DATE);
            float peso = Float.parseFloat(atributos[2]);
            float temperatura = Float.parseFloat(atributos[3]);
            Sexo sexo = Sexo.valueOf(atributos[4]);
            Cromosoma crom1 = Cromosoma.valueOf(atributos[5]);
            Cromosoma crom2 = Cromosoma.valueOf(atributos[6]);
            String campoTexto = atributos[7];
            if(campoTexto.equals("null")){
                campoTexto = "";
            }

            return new Raton(codigoRef, nacimiento, peso, temperatura, sexo, crom1, crom2, campoTexto);
        }// If the String pattern is invalid
        catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new IllegalArgument("Illegal CSV", ErrorType.IllegalCSV);
        } // If the String was unable to be parsed.
        catch (DateTimeParseException e) {
            throw new IllegalArgument("Illegal Date",ErrorType.IllegalDate);
        }
    }

    /**
     * Compara este ratón con el ratón especificado según su número de referencia.
     * @param o el ratón que se va a comprar
     * @return un entero negativo, cero o un entero positivo según este objeto sea menor, igual o mayor que el objeto especificado.
     */
    @Override
    public int compareTo(Raton o) {
        if(this.equals(o)){
            return 0;
        }else {
            return this.getCodigoReferencia()-o.getCodigoReferencia();
        }
    }

    /**
     * Comprueba si este ratón es igual al ratón especificado.
     * Dos ratones serán iguales si tienen el mismo código de referencia.
     * @param obj el objeto que se quiere comparar
     * @return true si son iguales, false si no lo son.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj!=null){
            if (obj instanceof Raton){
                Raton raton = (Raton) obj;
                    return this.getCodigoReferencia() == raton.getCodigoReferencia();
                }
            }
        return false;
    }


    public static void main(String[] args) throws IllegalArgument {

        //Test CSV
        /*Consola consola = new Consola();
        Raton raton = new Raton(2345, LocalDate.now(), 19.2f, 36.0f, Sexo.HEMBRA, " ");
        String csv = raton.toCSV();
        System.out.println(csv);
        Raton raton2 = raton.fromCSV(csv);
        System.out.println(raton2);*/

        //Test compareTo e equals
        Raton raton = new Raton(2345, LocalDate.now(),12f, 35f,  Sexo.HEMBRA, true, false, "");
        System.out.println(raton.toCSV(","));
        /*raton.setTemperatura(36);
        System.out.println(raton.getTemperatura());
        raton.setTemperatura(41);
        System.out.println(raton.getTemperatura());*/

        TreeSet<Raton> treeSet = new TreeSet<>();
        treeSet.add(raton);
        treeSet.add(new Raton(2346, LocalDate.of(2023, 3, 11), 12f, 35f, Sexo.HEMBRA, true, false, ""));
        treeSet.add(new Raton(2344, LocalDate.of(2023, 3, 11),12f, 35f,  Sexo.HEMBRA, true, false, ""));
        System.out.println(treeSet.stream().map(Raton::getCodigoReferencia).toList());

        TreeSet<Raton> treeSet2 = new TreeSet<>(new ComparatorRatonByNacimiento());
        treeSet2.addAll(treeSet);
        System.out.println(treeSet2.stream().map(Raton::getCodigoReferencia).toList());
        System.out.println(treeSet2.stream().map(Raton::getNacimiento).toList());

        TreeSet<Raton> treeSet3 = new TreeSet<>(new ComparatorRatonPesoDescendente());
        treeSet3.addAll(treeSet);
        System.out.println(treeSet3);
        System.out.println(treeSet3.stream().map(Raton::getCodigoReferencia).toList());
        System.out.println(treeSet3.stream().map(Raton::getNacimiento).toList());
    }

}

