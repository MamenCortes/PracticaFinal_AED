package org.Logica;

import org.Logica.Raton;

import java.util.Comparator;

/**
 * Compara dos ratones según su peso, de mayor a menor. Si este es igual, los compara según su código de referencia.
 */
public class ComparatorRatonPesoDescendente implements Comparator<Raton> {
    @Override
    public int compare(Raton o1, Raton o2) {
        if(o1.equals(o2)){
            return 0;
        }else { //codigoReferencia, fechaNacimiento, sexo,
            if((o1.getPeso()- o2.getPeso())==0){
                return o1.getCodigoReferencia()-o2.getCodigoReferencia();
            }else {
                return (int)(o2.getPeso()- o1.getPeso());
            }
        }
    }
}
