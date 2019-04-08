package escom.dm_solver

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import escom.dm_solver.classes.Genetico
import escom.dm_solver.classes.Session
import escom.dm_solver.classes.Vector
import kotlinx.android.synthetic.main.activity_output.*

class OutputActivity : AppCompatActivity() {

    private val tableParams = TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT)
    private val rowParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT)
    private val linearParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)

    private lateinit var table : TableLayout
    private lateinit var horizontalScrollView: HorizontalScrollView
    private lateinit var row: TableRow

    /* Differents Algoritms */
    private val session = Session.instance
    private var g = Genetico()

    var x = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_output)
        rowParams.setMargins(10,10,10,10)

        title = "Algoritmo Genético"
        geneticos()
    }

    fun geneticos(){
        g.calculateVectorSizes()
        g.createVectors()

        addTable()

            printHeader()
            g.vectores.forEach { v -> printVector(v)}

        commitTable()
    }

    private fun printHeader(){
        addRow()
        addElement("Vector",true)
        addElement("Valores",true)
        for (variable in session.funcionZ.variables) {
            addElement(variable,true)
        }
        addElement("z",true)
        commitRow()
    }

    fun printVector(v:Vector){
        addRow()
            addElement( v.tag )
            addElement( v.toString() )
            for(i in 0 until session.funcionZ.variables.size){
                addDouble( v.fenotipo(i) )
            }
            addDouble( session.funcionZ.eval( v.fenotipos ) )
        commitRow()
    }

    /* METHODS TO CREATE THE TABLE */
    private fun addTable(){
        horizontalScrollView = HorizontalScrollView(this)
        horizontalScrollView.layoutParams = linearParams

        table = TableLayout(this)
        table.layoutParams = linearParams
    }

    private fun addDouble(d:Double){ return addElement( "%.3f".format(d) ) }
    private fun addElement(element:String,strong :Boolean = false){
        var text = TextView(this)
        text.text = element
        text.layoutParams = rowParams
        text.textSize = 20.0f

        if(strong)
            text.typeface = Typeface.create(text.typeface,Typeface.BOLD)
        row.addView(text)
    }

    private fun addRow(){
        row = TableRow(this)
        row.layoutParams = tableParams
    }

    private fun commitRow(){    table.addView(row)  }
    private fun commitTable(){  horizontalScrollView.addView(table); container.addView(horizontalScrollView) }
}
