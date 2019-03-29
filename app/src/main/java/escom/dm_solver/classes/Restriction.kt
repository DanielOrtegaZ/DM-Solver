package escom.dm_solver.classes

import kotlin.collections.ArrayList

class Restriction {

    var coeficientes = ArrayList<Fraction>()
    var variables = ArrayList<String>()
    var operator = MAYOR_IGUAL
    var result = Fraction(0,1)

    companion object { // STATIC METHODS / VARIABLES
        fun createRestriction(s:String):Restriction?{

            //val regex = """([0-9]*|([0-9]+/[0-9]+))[a-y]((\+|-)([0-9]*|([0-9]+/[0-9]+))[a-y])*(<=|>=)[0-9]+""".toRegex()
            val regex = """([0-9]*|([0-9]+/[0-9]+))[a-y]((\+|-)([0-9]*|([0-9]+/[0-9]+))[a-y])*""".toRegex()
            var eqText = s.trim()

            if( regex.matches(eqText) )
                return Restriction()
            else
                return null
        }

        val MAYOR_IGUAL = 0
        val MENOR_IGUAL = 1
        val IGUAL = 2
    }
}