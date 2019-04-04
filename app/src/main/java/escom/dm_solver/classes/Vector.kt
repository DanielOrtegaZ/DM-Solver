package escom.dm_solver.classes

class Vector {

    var tag = "V1"
    var valores = ArrayList<Double>()
    var claves = ArrayList<String>()

    fun getGenotype(position:Int):Int{
        return 0
    }

    fun getFenotype(position:Int):Int {
        return 0
    }

    fun getFenotype(s:String):Int {
        return 0
    }

    fun mutacion():Vector?{
        return null
    }
}