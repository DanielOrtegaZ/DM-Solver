package escom.dm_solver.classes

class Fraction constructor(numerador:Int,denominador:Int){
    var num = numerador
    var den = denominador

    constructor(s:String): this( s.substringBefore('/').toInt(), if(s.contains('/')) s.substringAfter('/').toInt() else 1)

    companion object {
        /*fun simplifica(a:Fraction):Fraction{
            var i = 2
            var bandera=0
            while(i <= a.den){
                if(a.num%i == 0 && a.den%i == 0){
                    a.num = a.num/i
                    a.num = a.num/i
                    bandera = 1
                    break
                }
                i++
            }
            if(bandera==1){
                a=simplifica(a)
            }
            else{
                return a
            }
        }*/
        fun sum(a:Fraction,b:Fraction):Fraction{
            TODO( "Crear el método de suma" )
            a.num = a.num*b.den + b.num*a.den
            a.den = a.den*b.den
            //a = simplifica(a)
            return a
        }
        fun res(a:Fraction,b:Fraction):Fraction{
            TODO( "Crear el método de resta" )
            a.num = a.num*b.den - b.num*a.den
            a.den = a.den*b.den
            //a = simplifica(a)
            return a
        }
        fun mul(a:Fraction,b:Fraction):Fraction{
            TODO( "Crear el método de multiplicacion" )
            a.num = a.num*b.num
            a.den = a.den*b.den
            //a = simplifica(a)
            return a
        }
        fun div(a:Fraction,b:Fraction):Fraction{
            TODO( "Crear el método de division" )
            a.num = a.num*b.den
            a.den = a.den*b.num
            //a = simplifica(a)
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
