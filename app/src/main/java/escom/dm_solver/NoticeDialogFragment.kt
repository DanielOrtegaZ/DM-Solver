package escom.dm_solver


import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DialogTitle
import kotlinx.android.synthetic.main.input_dialog.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.input_dialog.view.*


class NoticeDialogFragment() : DialogFragment() {

    private var title = ""
    private var hint  = ""
    private var rId = 0
    var input = ""

    companion object {
        fun newInstance(rId : Int, title : String, hint : String) = NoticeDialogFragment().apply {
            arguments = Bundle().apply {
                putInt("ID",rId)
                putString("TITLE",title)
                putString("HINT",hint)
            }
        }
    }

    fun set(rId:Int,title: String,hint:String){
        this.rId = rId
        this.title = title
        this.hint = hint
    }

    // Use this instance of the interface to deliver action events
    internal var mListener: NoticeDialogListener? = null

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        arguments?.getInt("ID")?.let { rId = it }
        arguments?.getString("TITLE")?.let { title = it }
        arguments?.getString("HINT")?.let { hint = it }

        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = activity as NoticeDialogListener?

        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(activity.toString() + " must implement NoticeDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        var builder = AlertDialog.Builder(activity)

        val promptView = LayoutInflater.from(activity).inflate(R.layout.input_dialog, null)
        promptView.input_dialog_title.text = title
        promptView.input_dialog_edittext.hint = hint

        builder.setView(promptView)
            .setPositiveButton("OK") { _, _ ->
                input = promptView.input_dialog_edittext.text.toString()
                mListener?.onDialogPositiveClick(this)
            }
            .setNegativeButton("CANCEL"){ _, _ ->
                mListener?.onDialogNegativeClick(this)
            }

        return builder.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        return inflater.inflate(R.layout.input_dialog, container, false)
    }
}
