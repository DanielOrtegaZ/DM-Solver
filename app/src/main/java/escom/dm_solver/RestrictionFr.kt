package escom.dm_solver

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_restriction.view.*

class RestrictionFr : Fragment(), View.OnClickListener {

    var rid: Int = -1
    private var text: String? = null

    companion object {
        fun newInstance(id:Int,text:String) = RestrictionFr().apply {
            arguments = Bundle().apply {
                putInt("ID",id)
                putString("TEXT",text)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var viewReturned = inflater.inflate(R.layout.fragment_restriction, container, false)
        viewReturned.imageButton.setOnClickListener(this)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            viewReturned.label.text = Html.fromHtml(text,Html.FROM_HTML_MODE_COMPACT)
        else
            viewReturned.label.text = Html.fromHtml(text)

        return viewReturned
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getInt("ID")?.let { rid = it }
        arguments?.getString("TEXT")?.let { text = it }
    }

    override fun onClick(v:View?){
        var act = activity as InputActivity
        act.removeRestriction(rid,this)
    }

}
