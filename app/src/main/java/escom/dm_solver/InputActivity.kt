package escom.dm_solver

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_input.*
import kotlinx.android.synthetic.main.input_dialog.*

class InputActivity : AppCompatActivity(), NoticeDialogFragment.NoticeDialogListener {

    /* ACTIONS */
    val EDIT_Z : Int = 0
    val NEW_RULE : Int = 1

    var accion = NEW_RULE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        NoticeDialogFragment("Restriccion","12x+14y <= 12").show(supportFragmentManager,"NoticeDilogFragment")
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        var inputFragment = dialog as NoticeDialogFragment
        Toast.makeText(this,"Hola ${inputFragment.input}",Toast.LENGTH_LONG).show()
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        var inputFragment = dialog as NoticeDialogFragment
        Toast.makeText(this,"Hola ${inputFragment.input}",Toast.LENGTH_LONG).show()
    }

}
