package escom.dm_solver

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import escom.dm_solver.classes.FuncionZ
import escom.dm_solver.classes.Restriction
import escom.dm_solver.classes.Session
import kotlinx.android.synthetic.main.activity_input.*

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
        backBtn.setOnClickListener(this)

        if(savedInstanceState == null) {
            addNewRestriccion("12x + 13y <= 14")
            declareZ("z = 12x + 13y")
        }
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        //var inputFragment = dialog as NoticeDialogFragment
        //Toast.makeText(this,"Hola ${inputFragment.input}",Toast.LENGTH_LONG).show()
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        var inputFragment = dialog as NoticeDialogFragment
        var input = inputFragment.input

        when(accion){
            NEW_RULE -> addNewRestriccion(input)
            EDIT_Z -> declareZ(input)
        }
    }

    fun declareZ(input:String){

        val z = FuncionZ.createZ(input.replace("\\s".toRegex(),""))
        if(z != null){
            functionZ.text = z.toString()
            Session.instance.funcionZ = z
        }
        else
            Toast.makeText(this,"Invalud Input",Toast.LENGTH_SHORT).show()
    }

    fun addNewRestriccion(input : String){

        var restriction = Restriction.createRestriction(input.replace("\\s".toRegex(),""))
        if(restriction!=null) {

            Session.instance.restrictions.add(restriction)
            val t = supportFragmentManager.beginTransaction()
            val id = R.id.container
            Log.d("DM","Removing Restriction $x")
            t.add(id, RestrictionFr.newInstance(x++, restriction.toString()))
            t.commit()
        }
        else {
            Toast.makeText(this, "Invalud Input Z", Toast.LENGTH_SHORT).show()
        }
    }

    fun removeRestriction(id : Int, f : Fragment){
        val t = supportFragmentManager.beginTransaction()
        f as RestrictionFr
        Log.d("DM","Removing Restriction ${f.rid}")
        Session.instance.restrictions.set(f.rid,null)
        t.remove(f)
        t.commit()
    }

    override fun onClick(v : View){

        when(v.id){
            R.id.addRestrictionBtn -> {
                accion = NEW_RULE
                NoticeDialogFragment.newInstance(0,"Restriccion","12x + 13y <= 14")
                        .show(supportFragmentManager,"NoticeDialogFragment")
            }

            R.id.imageButton-> {
                accion = EDIT_Z
                NoticeDialogFragment.newInstance(0,"Funcion Óptima","z = 2x + 3y")
                        .show(supportFragmentManager,"NoticeDialogFragment")
            }

            R.id.maxMin -> {
                if(maxMin.isChecked)
                    maxMin.text = "Maximizar"
                else
                    maxMin.text = "Minimizar"
            }

            R.id.backBtn -> {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

}
