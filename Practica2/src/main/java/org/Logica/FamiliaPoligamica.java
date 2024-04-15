package org.Logica;

import org.Excepciones.ErrorType;
import org.Excepciones.IllegalArgument;

import java.time.LocalDate;
import java.util.TreeMap;

/**
 * Representa una familia de ratones con varias hembras y un macho propenso a la poligamia.
 */
public class FamiliaPoligamica extends Familia {
    private TreeMap<Integer, Raton> madres;
    /**
     * Constructor de la familia a partir de dos ratones: padre y madre.
     * @param padre el ratón padre
     * @param madre una de las madres
     */
    public FamiliaPoligamica(Raton padre, Raton madre){
        super.padre = padre;
        super.hijos = new TreeMap<Integer, Raton>();
        this.madres = new TreeMap<>();
        madres.put(madre.getCodigoReferencia(), madre);
    }

    /**
     * Añade una madre a la familia.
     * @param madre el ratón madre
     * @throws IllegalArgument si el ratón no es una hembra
     */
    public void addMadre(Raton madre) throws IllegalArgument {
        if(madre.getSexo().equals(Sexo.HEMBRA)){
            this.madres.put(madre.getCodigoReferencia(), madre);
        }else {
            throw new IllegalArgument("El ratón introducido no es una hembra", ErrorType.IllegalSex);
        }

    }

    @Override
    public void procrear(int lastPoblacionCode) throws IllegalArgument {
        int numHijos = 0;
        for (Raton madre:madres.values()) {
            if(!isMadreEsteril(madre)){
                int randomNum = (int) (Math.random()*100);
                if(randomNum>=95){
                    //8 crías
                    crearXHijos(madre, 8, lastPoblacionCode+numHijos);
                    numHijos = numHijos+8;
                } else if (randomNum>=75) {
                    //7 crías
                    crearXHijos(madre, 7, lastPoblacionCode+numHijos);
                    numHijos = numHijos+7;
                } else if (randomNum>=60) {
                    //6 crías
                    crearXHijos(madre, 6, lastPoblacionCode+numHijos);
                    numHijos = numHijos+6;
                } else if (randomNum>=45) {
                    //5 crías
                    crearXHijos(madre, 5, lastPoblacionCode+numHijos);
                    numHijos = numHijos+5;
                }else if(randomNum>=25){
                    //4 crías
                    crearXHijos(madre, 4, lastPoblacionCode+numHijos);
                    numHijos = numHijos+4;
                }else if(randomNum>=10){
                    //3 crías
                    crearXHijos(madre, 3, lastPoblacionCode+numHijos);
                    numHijos = numHijos+3;
                } else{
                    //2 crías
                    crearXHijos(madre, 2, lastPoblacionCode+numHijos);
                    numHijos = numHijos+2;
                }
            }
        }


    }
    private void crearXHijos(Raton madre, int xhijos, int baseCode) throws IllegalArgument {
        for(int i = 1; i<=xhijos; i++){
            Raton hijo = crearHijo(padre, madre, (baseCode+i));
            hijos.put(hijo.getCodigoReferencia(), hijo);
        }
    }

    /**
     * Devuelve una cadena de caracteres con la información de esta familia.
     * <ul>
     *     El formato será el siguiente:
     *     <p>Familia Poligámica: --------------------------</p>
     *     <p>Padre: [...]</p>
     *     <p>Madre: [...]</p>
     *     <p>(Demás madres)</p>
     *     <p>Hijo: [...]</p>
     *     <p>(Demás hijos)</p>
     *     <p>---------------------------------------</p>
     *</ul>
     * Para facilitar la legibilidad, la información de los ratones se mostrará en formato CSV.
     * @return una cadena de caracteres conteniendo la información de esta familia
     */
    @Override
    public String toString() {
        String toString = "Familia Poligámica: -------------------------" +
                "\nPadre: ["+padre.toCSV(",")+"]";
        for (Raton madre:madres.values()) {
            toString += "\nMadre: ["+madre.toCSV(",")+"]";
        }
        for (Raton hijo:hijos.values()) {
            toString += "\nHijo: ["+hijo.toCSV(",")+"]";
        }
        toString += "\n---------------------------------------";
        return toString;
    }

    public static void main(String[] args) {
        try {
            Raton padre = new Raton(1, LocalDate.now(), 15f, 35f, Sexo.MACHO, false, true, "Ratón sin patologías");
            Raton madre = new Raton(2, LocalDate.now(), 15f, 35f, Sexo.HEMBRA, true, false, "Ratón sin patologías");
            Raton madre2 = new Raton(3, LocalDate.now(), 15f, 35f, Sexo.HEMBRA, false, true, "Ratón sin patologías");
            FamiliaPoligamica familia = new FamiliaPoligamica(padre, madre);
            familia.addMadre(madre2);
            familia.procrear(2);
            System.out.println(familia);
            /*Iterator<Raton> iterador = familia.getHijos().values().iterator();
            System.out.println("Padre: "+padre.toCSV(","));
            System.out.println("Madre1: "+madre.toCSV(","));
            System.out.println("Madre2: "+madre2.toCSV(","));
            while (iterador.hasNext()){
                System.out.println("["+iterador.next().toCSV(",")+"]");
            }*/
        }catch (IllegalArgument iAex){
            System.out.println(iAex.getErrorType());
        }

    }

}
