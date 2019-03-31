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
        val aux = if(num>0) "+ " else "- "

        val varnum = if(num>0) num else -num
        if(den==1)
            return aux + "$varnum"
        else
            return aux + "$varnum/$den"
    }
}