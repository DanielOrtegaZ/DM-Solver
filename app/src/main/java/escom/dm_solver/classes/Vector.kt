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
        }
    }

    fun generate() {
        var random = 0
        for(i in 0 until sizes.size ) {
            random = Random.nextInt( (2.0).pow(sizes[i]).toInt() )
            genotipos.add( random )
            fenotipos.add( min[i] + random*constantes[i] )
        }
    }

    fun fenotipo(position:Int):Double{
        return fenotipos[position]
    }

    fun genotipo(position:Int):String {
        var m :String = genotipos[position].toString(2)
        while(m.length < sizes[position])
            m = "0$m"
        return m
    }

    override fun toString(): String {
        var m = ""
        for(i in 0 until genotipos.size) {
            //m += "|${genotipo(i)}"
            m += "|$i"
        }
        return "$tag = " + m.substring(1)
    }
}