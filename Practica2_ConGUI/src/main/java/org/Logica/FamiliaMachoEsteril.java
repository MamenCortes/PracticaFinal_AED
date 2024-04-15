package org.Logica;

import org.Excepciones.ErrorType;
import org.Excepciones.IllegalArgument;

import java.time.LocalDate;
import java.util.TreeMap;

/**
 * Representa una familia de ratones con una o varias hembras y un macho estéril
 */
public class FamiliaMachoEsteril extends Familia{
    private TreeMap<Integer, Raton> madres;
    /**
     * Constructor de la familia a partir de dos ratones: padre y madre.
     * @param padre el ratón padre
     * @param madre el ratón madre si no el macho no es poligámico, una de las madres si sí lo es
     */
    public FamiliaMachoEsteril(Raton padre, Raton madre){
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
                if(randomNum>=90){
                    //6 crías
                    crearXHijos(madre, 6, lastPoblacionCode+numHijos);
                    numHijos = numHijos+6;
                    //System.out.println("Ha tenido 6 hijos");
                } else if (randomNum>=70) {
                    //5 crías
                    crearXHijos(madre, 5, lastPoblacionCode+numHijos);
                    numHijos = numHijos+5;
                    //System.out.println("Ha tenido 5 hijos");
                } else if (randomNum>=35) {
                    //4 crías
                    crearXHijos(madre, 4, lastPoblacionCode+numHijos);
                    numHijos = numHijos+4;
                    //System.out.println("Ha tenido 4 hijos");
                } else if (randomNum>=15) {
                    //3 crías
                    crearXHijos(madre, 3, lastPoblacionCode+numHijos);
                    numHijos = numHijos+3;
                    //System.out.println("Ha tenido 3 hijos");
                }else{
                    //2 crías
                    crearXHijos(madre, 2, lastPoblacionCode+numHijos);
                    numHijos = numHijos+2;
                    //System.out.println("Ha tenido 2 hijos");
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
     *     <p>Familia Macho Estéril: --------------------------</p>
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
        String toString = "Familia Macho Estéril: --------------------" +
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
        try{
            Raton padre = new Raton(125, LocalDate.now(), 12.5f, 35f, Sexo.MACHO, true, false, "");
            Raton madre = new Raton(126, LocalDate.now(), 12.5f, 35f, Sexo.HEMBRA, true, false, "");
            Raton madre2 = new Raton(127, LocalDate.now(), 12.5f, 35f, Sexo.HEMBRA, false, true, "");

            FamiliaMachoEsteril familia = new FamiliaMachoEsteril(padre, madre);
            familia.addMadre(madre2);
            familia.procrear(126);
            System.out.println(familia);
        } catch (IllegalArgument e) {
            System.out.println(e.getErrorType());;
        }
    }
}
