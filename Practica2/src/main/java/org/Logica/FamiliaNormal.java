package org.Logica;

import org.Excepciones.IllegalArgument;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Representa una familia con una sola hembra y con un macho sin patologías
 */
public class FamiliaNormal extends Familia{
    /**
     * El ratón madre de la población
     */
    private Raton madre;

    /**
     * Constructor de la familia a partir de dos ratones: padre y madre.
     * @param padre el ratón padre
     * @param madre el ratón madre
     */
    public FamiliaNormal(Raton padre, Raton madre){
        super.padre = padre;
        this.madre = madre;
        super.hijos = new TreeMap<Integer, Raton>();
    }
    @Override
    public void procrear(int lastPoblacionCode) throws IllegalArgument {

        if(!isMadreEsteril(madre)){
            int randomNum = (int) (Math.random()*100);
            if(randomNum>=95){
                //9 crías
                crearXHijos(9, lastPoblacionCode);
            } else if (randomNum>=85) {
                crearXHijos(8, lastPoblacionCode);
                //8 crías
            } else if (randomNum>=70) {
                //7 crías
                crearXHijos(7, lastPoblacionCode);
            } else if (randomNum>=50) {
                //6 crías
                crearXHijos(6, lastPoblacionCode);
            }else if(randomNum>=30){
                //5 crías
                crearXHijos(5, lastPoblacionCode);
            }else if(randomNum>=15){
                //4 crías
                crearXHijos(4, lastPoblacionCode);
            } else if (randomNum>=5) {
                //3 crías
                crearXHijos(3, lastPoblacionCode);
            }else {
                //2 crías
                crearXHijos(2, lastPoblacionCode);
            }
        }
    }

    /**
     * Devuelve una cadena de caracteres con la información de esta familia.
     * <ul>
     *     El formato será el siguiente:
     *     <p>Familia Normal: --------------------------</p>
     *     <p>Padre: [...]</p>
     *     <p>Madre: [...]</p>
     *     <p>Hijo: [...]</p>
     *     <p>...</p>
     *     <p>---------------------------------------</p>
     *</ul>
     * Para facilitar la legibilidad, la información de los ratones se mostrará en formato CSV.
     * @return una cadena de caracteres conteniendo la información de esta familia
     */
    @Override
    public String toString() {
        String toString = "Familia Normal: --------------------------" +
                        "\nPadre: ["+padre.toCSV(",")+"]"+
                        "\nMadre: ["+madre.toCSV(",")+"]";
        for (Raton hijo:hijos.values()) {
            toString += "\nHijo: ["+hijo.toCSV(",")+"]";
        }
        toString += "\n---------------------------------------";
        return toString;
    }

    private void crearXHijos(int xhijos, int baseCode) throws IllegalArgument{
        for(int i = 1; i<=xhijos; i++){
            Raton hijo = crearHijo(padre, madre, (baseCode+i));
            hijos.put(hijo.getCodigoReferencia(), hijo);
        }
    }

    public static void main(String[] args) {
        try {
            Raton padre = new Raton(1, LocalDate.now(), 15f, 35f, Sexo.MACHO, false, false, "Ratón sin patologías");
            Raton madre = new Raton(2, LocalDate.now(), 15f, 35f, Sexo.HEMBRA, true, false, "Ratón sin patologías");
            FamiliaNormal familia = new FamiliaNormal(padre, madre);
            familia.procrear(2);

        }catch (IllegalArgument iAex){
            System.out.println(iAex.getErrorType());
        }

    }

}
