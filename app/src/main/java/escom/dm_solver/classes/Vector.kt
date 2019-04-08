package escom.dm_solver.classes

import kotlin.math.pow
import kotlin.random.Random

class Vector constructor(tag:String,mj:ArrayList<Int>,minValues:ArrayList<Double>,maxValues:ArrayList<Double>){
    var tag = tag
    var sizes = mj
    var min = minValues
    var constantes = ArrayList<Double>()
    var genotipos = ArrayList<Int>()
    var fenotipos = ArrayList<Double>()

    init {
        var aux = 0.0
        for(i in 0 until maxValues.size) {
            aux = ( maxValues[i] - minValues[i] ) / ( (2.0).pow(mj[i])-1 )
            constantes.add(aux)
            genotipos.add(0)
            fenotipos.add(0.0)
        }
    }

    fun generate() {
        var random = 0
        for(i in 0 until sizes.size ) {
            random = Random.nextInt( (2.0).pow(sizes[i]).toInt() )
            genotipos.set(i, random )
            fenotipos.set(i, min[i] + random*constantes[i] )
        }
    }

    /* Regresa el valor de la variable a,b,c,...,x,y */
    fun fenotipo(position:Int):Double{
        return fenotipos[position]
    }

    /* Regresa el numero aleatorio */
    fun genotipo(position:Int):String {
        var m :String = genotipos[position].toString(2)
        while(m.length < sizes[position])
            m = "0$m"
        return m
    }

    override fun toString(): String {
        var m = ""
        for(i in 0 until genotipos.size) {
            m += "  ${genotipo(i)}"
        }
        return m.substring(2)
    }
}