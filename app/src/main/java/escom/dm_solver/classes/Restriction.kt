package escom.dm_solver.classes

import android.util.Log
import kotlin.collections.ArrayList

class Restriction {

    var coeficientes = ArrayList<Fraction>()
    var variables = ArrayList<String>()
    var operator = MAYOR_IGUAL
    var result = Fraction(0,1)

    fun addTermino(s:String){

        val coef = s.substring(0..s.length-2)
        val variable = s.substring(s.length-1)

        if(coef.isBlank() || coef.equals("+"))
            coeficientes.add(Fraction(1, 1))

        else if (coef.equals("-"))
            coeficientes.add(Fraction(-1,1))

        else
            coeficientes.add(Fraction(coef))

        variables.add(variable)
    }

    companion object { // STATIC METHODS / VARIABLES

        val MAYOR_IGUAL = 0
        val MENOR_IGUAL = 1
        val IGUAL = 2

        fun createRestriction(s:String):Restriction?{

            val regex = """([+|-]?[0-9]*|([0-9]+/[0-9]+))[a-y]((\+|-)([0-9]*|([0-9]+/[0-9]+))[a-y])*((<=)|(>=)|=)[+|-]?[0-9]+(/[0-9]+)?""".toRegex()

            if( regex.matches(s) ){

                val r = Restriction()
                var termino = """([+|-]?[0-9]*|([0-9]+/[0-9]+))[a-y]""".toRegex().find(s)?.value
                r.addTermino(termino.toString())

                var inputx = s.substring(termino.toString().length)
                val found = """(\+|-)([0-9]*|([0-9]+/[0-9]+))[a-y]""".toRegex().findAll(inputx)
                found.forEach { f -> r.addTermino( f.value ) }

                if(found.count()>0)
                    inputx = inputx.substring( found.last().range.last+1 )

                var op = """(<=)|(>=)|=""".toRegex().find(inputx)?.value
                r.operator = if(op.equals("<=")) MENOR_IGUAL
                        else if(op.equals(">=")) MAYOR_IGUAL
                        else IGUAL

                inputx = inputx.substring(op.toString().length)
                r.result = Fraction( inputx )

                Log.d("DM","Fraction Created $r")
                return r
            }
            else
                return null
        }
    }

    override fun toString():String{
        var aux = ""

        if(coeficientes.size == variables.size)
            for(i in 0..coeficientes.size-1)
                aux += "${coeficientes.get(i)}<b>${variables.get(i)}</b> "
        else return ""

        if(aux.startsWith("+ "))
            aux = aux.substring(2)
        else
            aux = "-"+aux.substring(2)

        when(operator){
            MAYOR_IGUAL -> aux += "&qeq "
            MENOR_IGUAL -> aux += "&leq "
            IGUAL -> aux += "= "
        }

        aux += if(result.num>0)
                result.toString().substring(2)
            else
                "-"+result.toString().substring(2)

        return aux
    }
}