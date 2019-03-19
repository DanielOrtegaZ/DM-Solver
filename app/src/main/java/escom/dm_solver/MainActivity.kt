package escom.dm_solver

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        comenzar.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v){
            comenzar -> Toast.makeText(this,"Hey There",Toast.LENGTH_LONG).show()
        }
    }
}
