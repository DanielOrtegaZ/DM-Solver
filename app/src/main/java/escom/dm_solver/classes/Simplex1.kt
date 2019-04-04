package escom.dm_solver.classes

import escom.dm_solver.classes.Restriction.Companion.MAYOR_IGUAL

class  Simplex1 {
   /* companion object {

        fun calcular(restricciones: ArrayList<Restriction>,funcionZ: FunctionZ, numRestricciones: int, numMayorIgual: int, numeroVar:int) {

            var Cj = ArrayList<Fraction>()
            var nombreIndice = ArrayList<String>()
            var exponenteIndice = ArrayList<Fraction>()
            var valorIndice = ArrayList<ArrayList<Fraction>>()
            var numZj = ArrayList<Fraction>()
            var mZj = ArrayList<Fraction>()
            var numCjZj = ArrayList<Fraction>()
            var mCjZj = ArrayList<Fraction>()

            for (i in 0 until numRestricciones) {

                for (j in 0 until numeroVar) {
                    valorIndice[i].add(restricciones[j].coeficientes.get(j))
                }
                for(j in 0 until numRestricciones){
                    if(i-numeroVar == j) {
                        if (restricciones[j].operator.equals(MAYOR_IGUAL)) {
                            valorIndice[i].add(Fraction(1, 1))
                        } else {
                            valorIndice[i].add(Fraction(-1, 1))
                        }
                    }
                    else{
                        valorIndice[i].add(Fraction(0,1))
                    }
                }
                for(j in 0 until numRestricciones){
                    if (restricciones[j].operator.equals(MAYOR_IGUAL)) {
                        valorIndice[i].add(Fraction(1, 1))
                    } else {
                        valorIndice[i].add(Fraction(0, 1))
                    }

                }
            }
        }
    }*/

}