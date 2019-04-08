package escom.dm_solver.classes
import android.content.Context
import android.support.v4.content.IntentCompat
import android.util.Log
import android.widget.Toast
import escom.dm_solver.classes.Restriction.Companion.MAYOR_IGUAL
import escom.dm_solver.classes.Restriction.Companion.MENOR_IGUAL
import kotlin.math.*

class Genetico {

    /* Conjunto de Restricciones y FuncionZ */
    private var restriccion = ArrayList<Restriction>()
    private var funcion = FuncionZ()

    /* Number of Variables */
    private var numVar = 2
    private var numVect = 3

    /* Conjunto de Vectores */
    var vectores = ArrayList<Vector>()
    var zValues  = ArrayList<Double>()
    var zAcValues = ArrayList<Double>()

    /* Tamaño y numero de los vectores */
    private var mj = ArrayList<Int>()

    /* Valores de frontera de variables */
    val maxValues = ArrayList<Double>()
    val minValues = ArrayList<Double>()

    /*  */
    var min = ArrayList <Double>()
    var max = ArrayList <Double>()

    var exp = 0
    var iteracion = 3

    var coef = 0.0
    var valorZ = 0.0
    var valorTotalZ = 0.0
    var porcentaje = 0.0
    var porcentajeAcum = 0.0
    var valoresMin = ArrayList <Double>()
    var valoresMax = ArrayList <Double>()

    init {
        val session = Session.instance

        funcion   = session.funcionZ
        exp       = session.settings.bitPrecision
        numVect   = session.settings.numMiembros
        numVar    = session.funcionZ.variables.size
        iteracion = session.settings.numIteraciones

        session.restrictions.forEach { r ->
            if( r != null )
                restriccion.add(r)
        }
    }

    // TODO: Clean functions belonging to Classes. LIKE THIS ONE
    fun convertirDecimalABinario(n: Double, coef:Int): String {

        var n = n.toInt()
        var modulo: Int
        var binarioVolteado = ""
        while (n != 0) {
            modulo = (n % 2)
            binarioVolteado+=modulo
            n /= 2
        }
        for(i in 0 until (mj[coef]-1)){
            if(i>=binarioVolteado.length){
                binarioVolteado+="0"
            }
        }
        return binarioVolteado
    }

    private fun variablesRangeValues(max:ArrayList<Double>,min:ArrayList<Double>){

        var aux : Double
        for(i in 0 until numVar){
            min.add(1000000000.0)
            max.add(-100000000.0)
        }

        // Por cada restriccion
        restriccion.forEach { r ->
            // Por cada variable
            for(i in 0 until r.variables.size) {
                if (r.coeficientes[i].num != 0) {
                    // Actualizamos los límites
                    aux = r.findValue(i)
                    if (max[i] < aux) max[i] = aux
                    if (min[i] > aux) min[i] = aux
                }
            }
        }
    }

    private fun vectorSize(maxValue:Double,minValue:Double):Int{
        val bit = Session.instance.settings.bitPrecision
        val aux = ( maxValue - minValue )*( (10.0).pow(bit) )
        return ceil( log2(aux) ).toInt()
    }

    fun calculateVectorSizes(){

        variablesRangeValues(maxValues,minValues)
        for(i in 0 until funcion.variables.size) {
            Log.d("DM","${minValues[i]} <= ${funcion.variables[i]} <= ${maxValues[i]}")
        }

        for(i in 0 until funcion.variables.size) {
            mj.add( vectorSize(maxValues[i], minValues[i]) )
            Log.d("DM","mj(${funcion.variables[i]}) = ${mj[i]}")
        }
    }

    fun createVectors() {
        for(i in 0 until numVect) {
            var vector = Vector("V$i",mj,minValues,maxValues)

               do { vector.generate() }
            while ( !validate(vector) )

            Log.d("DM","${vector.tag} = $vector")
            vectores.add(vector)
            zValues.add(0.0)
            zAcValues.add(0.0)
        }
    }

    fun calcularZ() {
        for(i in 0 until numVect)
            zValues[i] = funcion.eval( vectores[i].fenotipos )
    }

    fun calcularZAcum(){
        var sum = 0.0;
        vectores.forEach { v -> sum += funcion.eval( v.fenotipos ) }

        for(i in 0 until numVect)
            zAcValues[i] = zValues[i] / sum
    }

    fun validate(vector: Vector):Boolean {
        var pass = true
        for (restriction in restriccion)
            if (!restriction.eval(vector.fenotipos)){
                pass = false; break
            }
        return pass
    }

    fun calcular(){

        min = minValues
        max = maxValues

        var vector = ArrayList<ArrayList<Double>>(emptyList()) //Los primeros numeros corresponden al binario de las variables, los siguientes a sus valores finales, incuyendo z. Luego siguen porcentajes y porcentajes acumulados respecto a z, finalmente numeros random del 0.1 al 0.10
        ////Aquí tengo que obtener cuantas variables existen en total (Maximo son 4)
        var result = 0.0

        valorTotalZ = 0.0
        porcentajeAcum = 0.0
        for(i in 0 until numVar + 1){
            valoresMin.add(1000000000.0)
            valoresMax.add(-100000000.0)
        }

        for(i in 0 until numVect){
            valorZ = 0.0
            var nuevalista = ArrayList<Double>()
            vector.add(nuevalista)
          //  Log.d("TAG", "Vector " + i)

            for(j in 0 until numVar){
                var exponente = ((2.0).pow(mj.get(j))).toInt()
                var binarioDecimal = (0 until exponente).random().toDouble()

                vector[i].add(binarioDecimal)
             //   Log.d("TAG", "Posicion " + j + ": " + vector[i][j])
            }

            for(j in 0 until numVar){
                vector[i].add( (min[j] + vector[i][j] * ( (max[j]-min[j]) / ((2.0).pow(mj[j]) -1) ) ) )
            }

            for(j in 0 until funcion.coeficientes.size){
                coef = (funcion.coeficientes.get(j).num).toDouble() / (funcion.coeficientes.get(j).den).toDouble()
                valorZ += coef * vector[i][j+numVar]
            }
            vector[i].add(valorZ)
            valorTotalZ += valorZ
        }

        //mucho ojo con los valores acumulables

        for(i in 0 until numVect){
            porcentaje = vector[i][numVar*2] / valorTotalZ
            porcentajeAcum += porcentaje
            vector[i].add(porcentaje)
            if(i == numVect -1) vector[i].add(1.0000000000000)
            else vector[i].add(porcentajeAcum)
            vector[i].add(((1..99).random()).toDouble() /100)
        }

        for(i in 0 until numVect){
            for(j in 0 until numVect){
                if(vector[i][numVar*2+3] <= vector[j][numVar*2+2]){
                    vector[i].add(j.toDouble())
                    break
                }
            }

            var bandera = 1
            for(j in 0 until restriccion.size){
                var acumulado = 0.0
                for(k in 0 until restriccion.get(j).coeficientes.size){
                    if(restriccion[j].coeficientes[k].num !=0 ) {
                        acumulado += vector[i][k + numVar] * (restriccion[j].coeficientes[k].num.toDouble() / restriccion[j].coeficientes[k].den.toDouble())
                    }
                }

                result = restriccion[j].result.num.toDouble()/restriccion[j].result.den.toDouble()
               // Log.d("Tag", "se va a comparar " + acumulado + " contra " + result)
                if(restriccion[j].operator.equals(MAYOR_IGUAL)){
                    if(acumulado < result){
                        bandera = 0
                        break
                    }
                }
                else if(restriccion[j].operator.equals(MENOR_IGUAL)){
                    if(acumulado > result){
                        bandera = 0
                        break
                     }
                }
            }
            if(bandera == 1){
                vector[i].add(1.0)
                if(valoresMin[numVar] > vector[i][numVar*2]) {
                    for(j in 0 until numVar+1){
                        valoresMin[j] = vector[i][numVar+j]
                    }
                }
                if(valoresMax[numVar] < vector[i][numVar*2]){
                    for(j in 0 until numVar+1){
                        valoresMax[j] = vector[i][numVar+j]
                    }
                }
            }else{
                vector[i].add(0.0)
            }

        }
        var respuesta = "\n"
        for(i in 0 until vector.size){
            for(j in 0 until vector[i].size){
                respuesta += vector[i][j].toString() + " "
            }
            respuesta+= "\n"
        }
        Log.d("Tag",respuesta)
        var maximos = "\n"
        var minimos = "\n"
        for(i in 0 until numVar + 1){
            maximos += valoresMax[i].toString() + " "
            minimos += valoresMin[i].toString() + " "
        }

        Log.d("Tag",minimos)
        Log.d("Tag",maximos)

        for(w in 1 until iteracion) {
            var cubeta = ArrayList<Int>()
            var numerosVect = ArrayList<Int>()
            var totalVectDif = 0
            var aux = 0
            for (i in 0 until numVect) {
                cubeta.add(0)
                numerosVect.add(i)
            }
            for (i in 0 until numVect) {
                if (cubeta[vector[i][numVar * 2 + 4].toInt()] == 0) {
                    totalVectDif++
                }
                cubeta[vector[i][numVar * 2 + 4].toInt()]++
            }
            for (i in 0 until numVect) {
                for (j in i until numVect) {
                    if (cubeta[i] < cubeta[j]) {
                        aux = cubeta[i]
                        cubeta[i] = cubeta[j]
                        cubeta[j] = aux
                        aux = numerosVect[i]
                        numerosVect[i] = numerosVect[j]
                        numerosVect[j] = aux
                    }
                }
            }
            var vectorCopia = ArrayList<ArrayList<Double>>(emptyList())
            for (i in 0 until numVect) {
                var nuevalista = ArrayList<Double>()
                vectorCopia.add(nuevalista)
                if (i < totalVectDif) {
                    for (j in 0 until vector[i].size) {
                        vectorCopia[i].add(vector[numerosVect[i]][j])
                    }
                }
            }

            vector = ArrayList<ArrayList<Double>>(emptyList())
            //Agrego mis vectores anteriores

            var nuevosVectores = "\n"
            var ruletaVector = 0
            valorTotalZ = 0.0
            porcentajeAcum = 0.0
            for (i in 0 until numVect) {
                var nuevalista = ArrayList<Double>()
                vector.add(nuevalista)
                if (i < totalVectDif) {
                    for (j in 0 until vectorCopia[i].size) {
                        vector[i].add(vectorCopia[i][j])
                        nuevosVectores += vector[i][j].toString() + " "
                    }
                } else {
                    var mjTotal = 0
                    for (j in 0 until mj.size) {
                        mjTotal += mj[j] - 1
                    }
                    var numBit = (0 until (mjTotal)).random()
                    var bitsAcumulados = 0
                    var exponenteCaeBit = mj.size - 1
                    var indiceBit = 0
                    for (j in 0 until mj.size) {
                        bitsAcumulados += mj[j] //le borré un uno
                        if (bitsAcumulados >= numBit) {
                            exponenteCaeBit = j
                            break;
                        }
                        indiceBit += mj[j]
                    }
                    indiceBit = numBit - indiceBit //tengo que cambiar mi exponente en el que cae el bit a binario y cambiarle en la pos ndicebit
                    for (j in 0 until vector[ruletaVector].size) {

                        if (j == exponenteCaeBit) {
                            ////////int i = 6
                            /*
                                0110 - 0010
                                1 << 2 = 100
                                0110 ^ 0100 = 0010
                                1 << i
                             */


                            var exponenteBinario = convertirDecimalABinario(vector[ruletaVector][j], j)
                            var exponenteBinarioMod = 0.0
                            //Log.d("Tag", exponenteBinario)
                            for (l in 0 until exponenteBinario.length) { ///tratar de no usar otro string y obtener el valor decimal en este for
                                if (l == exponenteBinario.length - indiceBit) {
                                    if (exponenteBinario.get(l) == '0') {
                                        exponenteBinarioMod += (2.0).pow(l)
                                    }

                                } else {
                                    if (exponenteBinario.get(l) == '1') {
                                        exponenteBinarioMod += (2.0).pow(l)
                                    }

                                }
                            }
                            vector[i].add(exponenteBinarioMod)
                        } else {
                            vector[i].add(vector[ruletaVector][j])
                        }

                    }

                    ruletaVector++
                    if (ruletaVector >= totalVectDif) ruletaVector = 0

                }

                //EN ESTA PARTE SE CONVIERTEN DE NUESTRO VALOR BINARIO AL DECIMAL Y SE AGREGAN AL VECTOR, TAMBIÉN SE OBTIENE Z
                valorZ = 0.0
                //  Log.d("TAG", "Vector " + i)

                for (j in 0 until numVar) {
                    vector[i][j + numVar] = min[j] + vector[i][j] * ((max[j] - min[j]) / ((2.0).pow(mj[j]) - 1))
                }

                for (j in 0 until funcion.coeficientes.size) {
                    coef = (funcion.coeficientes.get(j).num).toDouble() / (funcion.coeficientes.get(j).den).toDouble()
                    valorZ += coef * vector[i][j + numVar]
                }
                vector[i][numVar * 2] = valorZ
                valorTotalZ += valorZ
            }
            for (i in 0 until numVect) {
                porcentaje = vector[i][numVar * 2] / valorTotalZ
                porcentajeAcum += porcentaje
                vector[i][numVar * 2 + 1] = porcentaje
                if (i == numVect - 1) vector[i][numVar * 2 + 2] = 1.0000000000000
                else vector[i][numVar * 2 + 2] = porcentajeAcum
                vector[i][numVar * 2 + 3] = ((1..99).random()).toDouble() / 100
            }

            for (i in 0 until numVect) {
                for (j in 0 until numVect) {
                    if (vector[i][numVar * 2 + 3] <= vector[j][numVar * 2 + 2]) {
                        vector[i][numVar * 2 + 4] = j.toDouble()
                        break
                    }
                }
                var bandera = 1
                for (j in 0 until restriccion.size) {
                    var acumulado = 0.0
                    for (k in 0 until restriccion.get(j).coeficientes.size) {
                        if (restriccion[j].coeficientes[k].num != 0) {
                            acumulado += vector[i][k + numVar] * (restriccion[j].coeficientes[k].num.toDouble() / restriccion[j].coeficientes[k].den.toDouble())
                        }
                    }

                    result = restriccion[j].result.num.toDouble() / restriccion[j].result.den.toDouble()
                    // Log.d("Tag", "se va a comparar " + acumulado + " contra " + result)
                    if (restriccion[j].operator.equals(MAYOR_IGUAL)) {
                        if (acumulado < result) {
                            bandera = 0
                            break
                        }
                    } else if (restriccion[j].operator.equals(MENOR_IGUAL)) {
                        if (acumulado > result) {
                            bandera = 0
                            break
                        }
                    }
                }
                if (bandera == 1) {
                    vector[i][numVar * 2 + 5] = 1.0
                    if (valoresMin[numVar] > vector[i][numVar * 2]) {
                        for (j in 0 until numVar + 1) {
                            valoresMin[j] = vector[i][numVar + j]
                        }
                    }
                    if (valoresMax[numVar] < vector[i][numVar * 2]) {
                        for (j in 0 until numVar + 1) {
                            valoresMax[j] = vector[i][numVar + j]
                        }
                    }
                } else {
                    vector[i][numVar * 2 + 5] = 0.0
                }
            }

            var impresion = "\n"
            for (i in 0 until vector.size) {
                for (j in 0 until vector[i].size) {
                    impresion += vector[i][j].toString() + " "
                }
                Log.d("Tag", impresion)
                impresion = ""
            }

            maximos = "\n"
            minimos = "\n"

            for (i in 0 until numVar + 1) {
                maximos += valoresMax[i].toString() + " "
                minimos += valoresMin[i].toString() + " "
            }

            Log.d("Tag", minimos)
            Log.d("Tag", maximos)
        }
    }
}
