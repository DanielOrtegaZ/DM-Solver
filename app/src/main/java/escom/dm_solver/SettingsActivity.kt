package escom.dm_solver

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import escom.dm_solver.classes.Session
import kotlinx.android.synthetic.main.activity_settings.*

fun String.toEditable() : Editable = Editable.Factory.getInstance().newEditable(this)

class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    val PREFS_NAME = "escom.dm_solver.Preferences"
    val settings = Session.instance.settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        cancel.setOnClickListener(this)
        accept.setOnClickListener(this)

        bitPrecision.text = settings.bitPrecision.toString().toEditable()
        numPoblaciones.text = settings.numIteraciones.toString().toEditable()
        miembros.text = settings.numMiembros.toString().toEditable()
    }

    override fun onClick(v: View?) {

        if(v!=null)
        when(v.id){
            cancel.id -> {
                setResult(Activity.RESULT_CANCELED)
            }
            accept.id -> {
                setResult(Activity.RESULT_OK)
                saveSettings()
            }
        }

        finish()
    }

    private fun saveSettings(){
        settings.bitPrecision = bitPrecision.text.toString().toInt()
        settings.numIteraciones = numPoblaciones.text.toString().toInt()
        settings.numMiembros = miembros.text.toString().toInt()

        val prefEditor = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit()
        prefEditor.putInt("bit",settings.bitPrecision)
        prefEditor.putInt("iteraciones",settings.numIteraciones)
        prefEditor.putInt("miembros",settings.numMiembros)
        prefEditor.apply()
    }
}
