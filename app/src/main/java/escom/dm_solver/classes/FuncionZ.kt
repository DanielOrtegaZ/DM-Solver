package escom.dm_solver.classes

class FuncionZ {

    var coeficientes = ArrayList<Fraction>()
    var variables = ArrayList<String>()

    fun addTermino(s:String){

        val coef = s.substring(0..s.length-2)
        val variable = s.substring(s.length-1)
        var j=0

        for(varx in variables){
            if( varx.compareTo(variable) > 0 ) { break }
            j++
        }

        if(coef.isBlank() || coef.equals("+"))
            coeficientes.add(j,Fraction(1, 1))
        else if (coef.equals("-"))
            coeficientes.add(j,Fraction(-1,1))
        else
            coeficientes.add(j,Fraction(coef))
        variables.add(j,variable)
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

    fun eval(values :ArrayList<Double>):Double{
        var result = 0.0
        for(i in 0 until coeficientes.size){
            result += coeficientes[i].toDouble()*values[i]
        }
        return result
    }

    fun eval(values :ArrayList<Fraction>):Fraction{
        // TODO: Implementar metodo para fracciones
        return Fraction(1,1)
    }

    override fun toString():String{
        var aux = ""

        if(coeficientes.size == variables.size)
            for(i in 0 until coeficientes.size)
                aux += "${coeficientes[i]}${variables[i]} "
        else return ""

        if(aux.startsWith("+ "))
            aux = aux.substring(2)
        else
            aux = "-"+aux.substring(2)

        return "z = $aux"
    }
}