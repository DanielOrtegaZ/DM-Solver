package escom.dm_solver.classes

class Fraction constructor(numerador:Int,denominador:Int){
    var num = numerador
    var den = denominador

    constructor(s:String): this( s.substringBefore('/').toInt(), if(s.contains('/')) s.substringAfter('/').toInt() else 1)

    companion object {
        fun sum(a:Fraction,b:Fraction):Fraction{
            TODO( "Crear el método de suma" )
            return a
        }
    }

    override fun toString():String{
        return if(den==1) {
                   if (num == 1) "" else "${num}"
               } else "${num}/${den}"
    }
}