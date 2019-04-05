package escom.dm_solver

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import escom.dm_solver.classes.Session
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    val PREFS_NAME = "escom.dm_solver.Preferences"
    var option = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        comenzar.setOnClickListener(this)
        calculate.setOnClickListener(this)
        settings.setOnClickListener(this)
        loadSettings()

        val adapter = ArrayAdapter.createFromResource(this,R.array.methods,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    private fun loadSettings(){
        val settings = Session.instance.settings
        val preferences = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

        settings.bitPrecision = preferences.getInt("bit",0)
        settings.numIteraciones = preferences.getInt("iteraciones",3)
        settings.numMiembros = preferences.getInt("miembros",3)

        Log.d("DM","Settings ${settings.bitPrecision}")
        Log.d("DM","Settings ${settings.numIteraciones}")
    }

    override fun onClick(v: View) {
        when(v){
            comenzar -> {
                val intent = Intent(this,InputActivity::class.java)
                startActivityForResult(intent,200)
            }
            settings -> {
                val intent = Intent(this,SettingsActivity::class.java)
                startActivityForResult(intent,300)
            }
            calculate -> {
                val intent = Intent(this,OutputActivity::class.java)
                startActivityForResult(intent,400)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            200 -> if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this,"Hey There",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val value = parent!!.getItemAtPosition(position)
        Toast.makeText(this,"Se atrapo a ${value}",Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
