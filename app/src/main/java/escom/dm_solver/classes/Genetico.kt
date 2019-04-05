package escom.dm_solver.classes
import android.support.v4.content.IntentCompat
import android.util.Log
import escom.dm_solver.classes.Restriction.Companion.MAYOR_IGUAL
import escom.dm_solver.classes.Restriction.Companion.MENOR_IGUAL
import kotlin.math.*

class Genetico {

    var restriccion = ArrayList<Restriction>()
    var funcion = Session.instance.funcionZ
    var numVar = 2 // Numero de variables diferentes que tienen tanto nuestras restricciones como nuestra funcion *Lo tengo que implementar*
    var exp = 2.0 // Exponente del 10
    var numVect = 5 // Numero de vectores que se tienen que hacer
    var iteracion = 1 //Numero de iteraciones que se tienen que hacer
    var min = ArrayList <Double>()
    var max = ArrayList <Double>()
    var mj = ArrayList<Int>()
    var result = 0.0
    var coef = 0.0
    var valorZ = 0.0
    var valorTotalZ = 0.0
    var porcentaje = 0.0
    var porcentajeAcum = 0.0
    var valoresMin = ArrayList <Double>()
    var valoresMax = ArrayList <Double>()

    init {
        val res = Session.instance.restrictions
        res.forEach { r ->
            if( r != null )
                restriccion.add(r)
        }
    }

    fun convertBinaryToDecimal(num: Long): Int {
        var num = num
        var decimalNumber = 0
        var i = 0
        var remainder: Long

        while (num.toInt() != 0) {
            remainder = num % 10
            num /= 10
            decimalNumber += (remainder * Math.pow(2.0, i.toDouble())).toInt()
            ++i
        }
        return decimalNumber
    }

    fun convertDecimalToBinary(n: Double): Double {
        var n = n
        var binaryNumber: Double = 0.0
        var remainder: Int
        var i = 1
        var step = 1
        var binario:String = ""
        while (n != 0.0) {
            remainder = (n % 2).toInt()
            n /= 2
            binaryNumber += (remainder * i).toDouble()
            i *= 10
        }
        return binaryNumber
    }

    fun calcular(){

        var vector = ArrayList<ArrayList<Double>>(emptyList()) //Los primeros numeros corresponden al binario de las variables, los siguientes a sus valores finales, incuyendo z. Luego siguen porcentajes y porcentajes acumulados respecto a z, finalmente numeros random del 0.1 al 0.10

        ////Aquí tengo que obtener cuantas variables existen en total (Maximo son 4)


        valorTotalZ = 0.0
        porcentajeAcum = 0.0
        for(i in 0 until numVar){
            min.add(1000000000.0)
            max.add(-100000000.0)
        }
        for(i in 0 until numVar + 1){
            valoresMin.add(1000000000.0)
            valoresMax.add(-100000000.0)
        }
        //////////////
        Log.d("TAG", "Valores de Z")
        for(i in 0 until funcion.coeficientes.size){
            coef = (funcion.coeficientes.get(i).num).toDouble() / (funcion.coeficientes.get(i).den).toDouble()
            Log.d("TAG", "valor coeficiente: " +  coef)
        }
        //////////////
        Log.d("TAG", "Restricciones")
        Log.d("TAG", "Restriccion size: " +  restriccion.size)
        for(i in 0 until restriccion.size){
             result = (restriccion.get(i).result.num).toDouble() / (restriccion.get(i).result.den).toDouble()
            Log.d("TAG", "Restriccion numero: " + i)
            for(j in 0 until restriccion.get(i).coeficientes.size){
                if(restriccion.get(i).coeficientes.get(j).num != 0 ) {
                    coef = (restriccion.get(i).coeficientes.get(j).num).toDouble() / (restriccion.get(i).coeficientes.get(j).den).toDouble()
                    Log.d("TAG", "valor coeficiente: " +  coef)
                    if (result/coef > max[j]) {
                        max[j] = result / coef
                    }
                    if (result/coef < min[j]) {
                        min[j] = result / coef
                    }
                }
                else{
                    Log.d("TAG", "valor coeficiente: " +  coef)
                }
            }
            Log.d("TAG", "Simbolo: " + restriccion.get(i).operator)
            Log.d("TAG", "valor result: " +  result)
        }
        for (i in 0 until min.size){

            Log.d("TAG", "Valor minimo en posicion " + i + " " + min[i].toString()+ " ")
        }
        for (i in 0 until max.size){
            Log.d("TAG", "Valor maximo en posicion " + i + " " + max[i].toString()+ " ")
        }
        for(i in 0 until numVar){
             var lognum = log((max[i]-min[i])*((10.0).pow(exp)),10.0).toDouble()
             var logden = log(2.0,10.0).toDouble()
            Log.d("TAG", "Valor de mj en " + i + " " + lognum + "/" + logden + " mas 1")
        }

        for(i in 0 until numVar){
            mj.add ( ( (log((max[i]-min[i])*((10.0).pow(exp)),10.0).toDouble() ) / (log(2.0,10.0).toDouble()) ).toInt() + 1)
            Log.d("TAG", "Valor entero de mj en " + i + " " + mj[i])
        }

        for(i in 0 until numVect){
            valorZ = 0.0
            var nuevalista = ArrayList<Double>()
            vector.add(nuevalista)
            Log.d("TAG", "Vector " + i)
            for(j in 0 until numVar){
                var exponente = ((2.0).pow(mj.get(j)-1)).toInt()
                var binarioDecimal = (0 until exponente).random().toDouble()

                vector[i].add(binarioDecimal)
                Log.d("TAG", "Posicion " + j + ": " + vector[i][j])
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

        //mucho ojo con los valores acumulables y puto el que lo lea

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
                Log.d("Tag", "se va a comparar " + acumulado + " contra " + result)
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

        var cubeta = ArrayList<Int>()
        var numerosVect = ArrayList<Int>()
        var totalVectDif = 0
        var aux = 0
        for(i in 0 until numVect){
            cubeta.add(0)
            numerosVect.add(i)
        }
        for(i in 0 until numVect){
            if(cubeta[vector[i][numVar*2+4].toInt()] == 0){
                totalVectDif++
            }
            cubeta[vector[i][numVar*2+4].toInt()]++
        }
        for(i in 0 until numVect){
            for(j in i until numVect){
                if(cubeta[i] < cubeta[j]){
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
        for(i in 0 until numVect ){
            var nuevalista = ArrayList<Double>()
            vectorCopia.add(nuevalista)
            if(i < totalVectDif) {
                for (j in 0 until vector[i].size) {
                    vectorCopia[i].add(vector[numerosVect[i]][j])
                }
            }
        }
        vector =  ArrayList<ArrayList<Double>>(emptyList())
        //Agrego mis vectores anteriores
        var nuevosVectores = "\n"
        var ruletaVector = 0
        for(i in 0 until numVect ) {
            var nuevalista = ArrayList<Double>()
            vector.add(nuevalista)
            if(i < totalVectDif){
                for (j in 0 until vectorCopia[i].size) {
                    vector[i].add(vectorCopia[i][j])
                    nuevosVectores += vector[i][j].toString() + " "
                }
                Log.d("Tag", nuevosVectores)
                nuevosVectores = ""
            }
            else{
                var mjTotal = 0
                for(j in 0 until mj.size){
                    mjTotal+=mj[j] - 1
                }
                var numBit = (0 until (mjTotal)).random()
                var bitsAcumulados = 0
                var exponenteCaeBit = mj.size -1
                var indiceBit = 0
                for(j in 0 until mj.size){
                    bitsAcumulados += mj[j] -1
                    if(bitsAcumulados>=numBit){
                        exponenteCaeBit = j
                        break;
                    }
                    indiceBit += mj[j] - 1
                }
                indiceBit = numBit - indiceBit //tengo que cambiar mi exponente en el que cae el bit a binario y cambiarle en la pos ndicebit
                for(j in 0 until vector[ruletaVector].size){

                    if(j == exponenteCaeBit){
                        var exponenteBinario = convertDecimalToBinary(vector[ruletaVector][j]).toString()
                        var exponenteBinarioMod : String = ""
                        for(l in 0 until exponenteBinario.length){ ///tratar de no usar otro string y obtener el valor decimal en este for
                            if(l == indiceBit){
                                if(exponenteBinario.get(indiceBit)=='0')  exponenteBinarioMod += "1"
                                else exponenteBinarioMod += "0"
                            }
                            else exponenteBinarioMod += exponenteBinario.get(l)
                        }

                    }
                    else{
                        vector[i].add(vector[ruletaVector][j])
                    }
                }
                ruletaVector++
                if(ruletaVector>=totalVectDif) ruletaVector = 0
            }
        }

    }
}
