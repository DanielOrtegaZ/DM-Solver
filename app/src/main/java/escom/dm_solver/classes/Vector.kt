package escom.dm_solver.classes

import android.util.Log
import kotlin.math.pow
import kotlin.random.Random

class Vector constructor(tag:String,mj:ArrayList<Int>,minValues:ArrayList<Double>,maxValues:ArrayList<Double>,calculate:Boolean = true){
    var tag = tag
    var sizes = mj
    var min = minValues
    var constantes = ArrayList<Double>()
    var genotipos = ArrayList<Int>()
    var fenotipos = ArrayList<Double>()
    var freq = 0

    constructor(tag:String,mj:ArrayList<Int>,minValues: ArrayList<Double>) : this(tag,mj,minValues,ArrayList(),false)

    init {
        var aux : Double
        if(calculate)
        for (i in 0 until maxValues.size) {
            aux = (maxValues[i] - minValues[i]) / ((2.0).pow(mj[i]) - 1)
            constantes.add(aux)
            genotipos.add(0)
            fenotipos.add(0.0)
        }
    }

    fun mutar(tag:String):Vector{
        val v = Vector(tag,sizes,min)
        val i = Random.nextInt(sizes.size)
        val random = (2.0).pow( Random.nextInt(sizes[i]) ).toInt()

        v.constantes.addAll(constantes)
        v.genotipos.addAll(genotipos)
        v.fenotipos.addAll(fenotipos)
        v.genotipos[i] = genotipos[i] or random
        v.fenotipos[i] = min[i] + random*constantes[i]

        if(v.genotipos[i] == genotipos[i])
            v.genotipos[i] -= random
        return v
    }

    fun generate() {
        var random = 0
        for(i in 0 until sizes.size ) {
            random = Random.nextInt( (2.0).pow(sizes[i]).toInt() )
            genotipos[i] = random
            fenotipos[i] = min[i] + random*constantes[i]
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