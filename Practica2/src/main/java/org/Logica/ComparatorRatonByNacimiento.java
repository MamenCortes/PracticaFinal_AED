package org.Logica;

import org.Logica.Raton;

import java.util.Comparator;

/**
 * Compara dos ratones según su fecha de nacimiento. Si esta es igual, los compara según su código de referencia.
 */
public class ComparatorRatonByNacimiento implements Comparator<Raton> {
    @Override
    public int compare(Raton o1, Raton o2) {
        if(o1.equals(o2)){
            return 0;
        }else {
            if(o1.getNacimiento().isEqual(o2.getNacimiento())){
                return o1.getCodigoReferencia()-o2.getCodigoReferencia();
            }else {
                return o1.getNacimiento().compareTo(o2.getNacimiento());
            }

        }

    }
}
