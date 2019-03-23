package escom.dm_solver

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_input.*
import kotlinx.android.synthetic.main.input_dialog.*

class InputActivity : AppCompatActivity(), NoticeDialogFragment.NoticeDialogListener, View.OnClickListener {

    /* ACTIONS */
    val EDIT_Z : Int = 0
    val NEW_RULE : Int = 1
    var accion = NEW_RULE

    var x = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        setSupportActionBar(toolbar)

        addRestrictionBtn.setOnClickListener(this)
        imageButton.setOnClickListener(this)
        maxMin.setOnClickListener(this)

        addNewRestriccion("12x + 13y <= 14")
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        //var inputFragment = dialog as NoticeDialogFragment
        //Toast.makeText(this,"Hola ${inputFragment.input}",Toast.LENGTH_LONG).show()
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        var inputFragment = dialog as NoticeDialogFragment
        var input = inputFragment.input

        Toast.makeText(this,"INPUT: ${input}",Toast.LENGTH_LONG).show()

        when(accion){
            NEW_RULE -> addNewRestriccion(input)
            EDIT_Z -> declareZ(input)
        }
    }

    fun addNewRestriccion(input : String){
        val t = supportFragmentManager.beginTransaction()
        val id = R.id.container

        t.add(id,RestrictionFr.newInstance(x++,input))
        t.commit()
    }

    fun removeRestriction(id : Int, f : Fragment){

        val t = supportFragmentManager.beginTransaction()
        t.remove(f)
        t.commit()
        //val id = R.id.container
    }

    fun declareZ(input:String){}

    override fun onClick(v : View?){

        when(v){
            addRestrictionBtn -> {
                accion = NEW_RULE
                NoticeDialogFragment.newInstance(0,"Restriccion","12x + 13y <= 14")
                        .show(supportFragmentManager,"NoticeDialogFragment")
            }

            imageButton -> {
                accion = EDIT_Z
                NoticeDialogFragment.newInstance(0,"Restriccion","12x + 13y <= 14")
                        .show(supportFragmentManager,"NoticeDialogFragment")
            }

            maxMin -> {
                if(maxMin.isChecked)
                    maxMin.text = "Maximizar"
                else
                    maxMin.text = "Minimizar"
            }

        }
    }

}
