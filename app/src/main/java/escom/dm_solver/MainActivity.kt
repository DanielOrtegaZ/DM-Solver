package escom.dm_solver

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.app.Activity
import android.content.Intent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    var option = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        comenzar.setOnClickListener(this)
        calculate.setOnClickListener(this)

        val adapter = ArrayAdapter.createFromResource(this,R.array.methods,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    override fun onClick(v: View) {
        when(v){
            comenzar -> {
                val intent = Intent(this,InputActivity::class.java)
                startActivityForResult(intent,200)
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
