package org.Other;

import org.InputOutput.Consola;

public class Pulverizador {
    public static void main(String[] args) {
        int res = 1;
        int dividendo;
        int divisor;
        int cociente;
        System.out.println("Dividendo: ");
        dividendo = Consola.inputInt();
        System.out.println("Divisor: ");
        divisor = Consola.inputInt();
        while (res > 0) {
            cociente = dividendo / divisor;
            if(dividendo % divisor == 0){
                System.out.println("El mcd es: "+res);
                break;
            }else {
                res = dividendo % divisor;
            }

            System.out.println(dividendo+"= "+divisor+" x "+cociente+" + "+res);
            dividendo = divisor;
            divisor = res;
        }
    }
}
