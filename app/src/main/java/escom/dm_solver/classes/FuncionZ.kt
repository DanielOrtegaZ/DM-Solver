package escom.dm_solver.classes

import android.util.Log

class FuncionZ {

    var coeficientes = ArrayList<Fraction>()
    var variables = ArrayList<String>()

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

        fun createZ(s:String):FuncionZ?{

            val regex = """z=([+|-]?[0-9]*|([0-9]+/[0-9]+))[a-y]((\+|-)([0-9]*|([0-9]+/[0-9]+))[a-y])*""".toRegex()

            if( regex.matches(s) ){

                var inputx = s.substring(2)

                val r = FuncionZ()
                var termino = """([+|-]?[0-9]*|([0-9]+/[0-9]+))[a-y]""".toRegex().find(inputx)?.value
                r.addTermino(termino.toString())

                inputx = s.substring(termino.toString().length)
                val found = """(\+|-)([0-9]*|([0-9]+/[0-9]+))[a-y]""".toRegex().findAll(inputx)
                found.forEach { f -> r.addTermino( f.value ) }
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
                aux += "${coeficientes.get(i)}${variables.get(i)} "
        else return ""

        if(aux.startsWith("+ "))
            aux = aux.substring(2)
        else
            aux = "-"+aux.substring(2)

        return "z = $aux"
    }

}