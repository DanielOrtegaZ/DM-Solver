package escom.dm_solver.classes

import android.util.Log
import kotlin.collections.ArrayList

class Restriction {

    var coeficientes = ArrayList<Fraction>()
    var variables = ArrayList<String>()
    var operator = MAYOR_IGUAL
    var result = Fraction(0,1)

    fun addTermino(s:String){
        Log.d("DM","addTermino ${s}")
        //Toast.makeText(this,"${f.value.substring( 0..f.value.length-2 )}", Toast.LENGTH_SHORT).show()
        //Toast.makeText(this,"${f.value.substring( f.value.length-1 )}", Toast.LENGTH_SHORT).show()
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
                        else if(op.equals("<=")) MAYOR_IGUAL
                        else IGUAL

                inputx = inputx.substring(op.toString().length)
                r.result = Fraction( inputx )

                Log.d("DM","operator = ${r.operator}")
                Log.d("DM","result = ${r.result}")

                return r
            }
            else
                return null
        }
    }

    /*override fun toString():String{
        var ret = ""

        for(i=0;i<coeficientes.size();i++)
            ret += "${coeficientes.get(i)} ${variables.get(i)}"

    }*/
}