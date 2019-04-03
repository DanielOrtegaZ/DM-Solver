package escom.dm_solver.classes

class Simplex1 {
    companion object {

        fun calcular(restricciones: Restriction,funcionZ: FunctionZ, numRestricciones: int, numMayorIgual: int) {
            var Cj = ArrayList<Fraction>()
            var nombreIndice = ArrayList<String>()
            var exponenteIndice = ArrayList<Fraction>()
            var valorIndice = ArrayList<Fraction>()
            var numZj = ArrayList<Fraction>()
            var mZj = ArrayList<Fraction>()
            var numCjZj = ArrayList<Fraction>()
            var mCjZj = ArrayList<Fraction>()

            for (i in 0 until restricciones.size) {

                for (j in 0 until restricciones.get(i).size) {
                    restricciones[i][j] = 2;
                }
            }
        }
    }

}