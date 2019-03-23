package escom.dm_solver

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.app.Activity
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        comenzar.setOnClickListener(this)
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

}
