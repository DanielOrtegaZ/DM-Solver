package escom.dm_solver.classes
import android.util.Log
import escom.dm_solver.classes.Restriction.Companion.MAYOR_IGUAL
import escom.dm_solver.classes.Restriction.Companion.MENOR_IGUAL
import kotlin.math.*

class Genetico {

    var restriccion = Session.instance.restrictions
    var funcion = Session.instance.funcionZ //
    var numVar = 2 //
    var exp = 2.0 //
    var numVect = 5 //
    var min = ArrayList <Double>()
    var max = ArrayList <Double>()
    var mj = ArrayList<Int>()
    var vector = ArrayList<ArrayList<Double>>() //Los primeros numeros corresponden al binario de las variables, los siguientes a sus valores finales, incuyendo z. Luego siguen porcentajes y porcentajes acumulados respecto a z, finalmente numeros random del 0.1 al 0.10
    var result = 0.0
    var coef = 0.0
    var valorZ = 0.0
    var valorTotalZ = 0.0
    var porcentaje = 0.0
    var porcentajeAcum = 0.0

    fun calcular(){

        ////Aquí tengo que obtener cuantas variables existen en total (Maximo son 4)
        //log.d("tag", variableString)
        valorTotalZ = 0.0
        porcentajeAcum = 0.0
        for(i in 0 until numVar){
            min.add(1000000000.0)
            max.add(-100000000.0)
        }
        for(i in 0 until restriccion.size){
             result = (restriccion.get(i).result.num).toDouble() / (restriccion.get(i).result.den).toDouble()
            for(j in 0 until restriccion.get(i).coeficientes.size){
                if(restriccion.get(i).coeficientes.get(j).num != 0 ) {
                    coef = (restriccion.get(i).coeficientes.get(j).num).toDouble() / (restriccion.get(i).coeficientes.get(j).den).toDouble()
                    if (coef / result > max[j]) {
                        max[j] = coef / result
                    }
                    if (coef / result < min[j]) {
                        min[j] = coef / result
                    }
                }
            }
        }
        for(i in 0 until numVar){
            mj[i] = (log((max[i]-min[i])*((10.0).pow(exp)),10.0) / log(2.0,10.0) + 1).toInt()
        }
        for(i in 0 until numVect){
            valorZ = 0.0
            for(j in 0 until numVar){
                vector[i].add((0..((2.0).pow(mj[j])-1).toInt()).random().toDouble())
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
            if(i == numVect -1) vector[i].add(1.0)
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
                    acumulado += vector[i][k+numVar]
                }
                result = restriccion[j].result.num.toDouble()/restriccion[j].result.den.toDouble()
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
            }else{
                vector[i].add(0.0)
            }

        }
        var respuesta = ""
        for(i in 0 until vector.size){
            for(j in 0 until vector[i].size){
                respuesta += vector[i][j].toString() + " "
            }
            respuesta+= "\n"
        }
        Log.d("Tag",respuesta)
    }

}
