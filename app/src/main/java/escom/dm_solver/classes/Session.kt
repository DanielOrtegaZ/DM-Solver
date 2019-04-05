package escom.dm_solver.classes

import java.util.ArrayList

class Session private constructor(){

    init { }
    private object Holder { val INSTANCE = Session() }

    companion object {
        val instance : Session by lazy { Holder.INSTANCE }
    }

    var restrictions = ArrayList<Restriction?>()
    var funcionZ = FuncionZ()
    var maximizar = true
    var settings = Settings()

    class Settings {
        var bitPrecision = 1
        var numIteraciones = 3
        var numMiembros = 5
    }
}