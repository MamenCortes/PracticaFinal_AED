package org.Logica;

import org.Excepciones.ErrorType;
import org.Excepciones.IllegalArgument;

import java.time.LocalDate;
import java.util.TreeMap;

/**
 * Interfaz que representa a una familia de ratones.
 */
public abstract class Familia {
    /**
     * El ratón padre de la población
     */
    protected Raton padre;
    /**
     * TreeMap que almacenará los ratones hijos de la población.
     * <p>La clave será el código de referencia.</p>
     */
    protected TreeMap<Integer, Raton> hijos;

    /**
     * Método que simula la reproducción de las familias.
     * <p>Si la hembra es estéril, la familia no procreará</p>
     * <p>Cada hembra tendrá un número aleatorio de hijos. La fertilidad dependerá del tipo de familia.</p>
     * <p>Los códigos de referencia de los hijos se crearán a partir de un código base, siendo siempre mayores que este.
     * De esta forma se pretende prevenir la repetición de códigos.</p>
     * @param lastPoblacionCode el código de referencia máximo desde donde se llama al método
     * @throws IllegalArgument si la temperatura o el peso de alguno de los ratones hijos no es válido. O si uno de los ratones madre no es Hembra.
     */
    public abstract void procrear(int lastPoblacionCode) throws IllegalArgument;

    /**
     * Indica si un ratón Hembra es estéril o no.
     * <p>Para ser estéril, una hembra tiene que tener sus dos cromosomas X mutados</p>
     * @param madre el ratón hembra
     * @return true si es estéril, false si no lo es.
     * @throws IllegalArgument si el ratón especificado no es una hembra
     */
    protected final boolean isMadreEsteril(Raton madre) throws IllegalArgument {
        if (madre.getSexo().equals(Sexo.HEMBRA)){
            return madre.getCromosoma1().equals(Cromosoma.Xmut)&&madre.getCromosoma2().equals(Cromosoma.Xmut);
        }else {
            throw new IllegalArgument("El ratón introducido no es Hembra", ErrorType.IllegalSex);
        }

    }

    /**
     * Crea un ratón hijo que herede sus cromosomas de un padre y una madre.
     * <p>
     *     Una cría tiene un 50% de probabilidad de heredar el cromosoma X del padre, un 50% de heredar el cromosoma Y del padre,
     *     y un 50% de probabilidad de heredar cada uno de los cromosomas X de la madre. </p>
     * <p>La herencia se decidirá de manera aleatoria.  </p>
     *
     * @param padre el ratón padre
     * @param madre el ratón madre
     * @param code el código de referencia del ratón hijo
     * @return una cría
     * @throws IllegalArgument si el peso o la temperatura del ratón no es válido.
     */
    protected final Raton crearHijo(Raton padre, Raton madre, int code) throws IllegalArgument {
        Cromosoma crom1, crom2;
        Sexo sexo;
        int p = (int) (Math.random()*100);
        int m = (int) (Math.random()*100);
        if(p<50){
            crom2 = padre.getCromosoma2();
            sexo = Sexo.MACHO;
        }else{
            crom2 = padre.getCromosoma1();
            sexo = Sexo.HEMBRA;
        }
        if (m<50){
            crom1 = madre.getCromosoma1();
        }else {
            crom1 = madre.getCromosoma2();
        }

        return new Raton(code, LocalDate.now(), 15f, 35f, sexo, crom1, crom2, "");
    }

    /**
     * Devuelve una referencia a la lista de ratones hijos.
     * @return la referencia a this.hijos
     */
    protected final TreeMap<Integer, Raton> getHijos(){
        return this.hijos;
    }
}
