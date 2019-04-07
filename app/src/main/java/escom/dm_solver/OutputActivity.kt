package escom.dm_solver

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import escom.dm_solver.classes.Genetico
import kotlinx.android.synthetic.main.activity_output.*

class OutputActivity : AppCompatActivity() {

    val tableParams = TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT)
    val rowParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT)
    val linearParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)

    private lateinit var table : TableLayout
    private lateinit var row: TableRow

    var x = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_output)
        rowParams.setMargins(10,10,10,10)
        geneticos()
    }

    fun geneticos(){
        var g = Genetico()
        g.calculateRangeOfVariables()
        //algGen.calcular()

        var header = arrayListOf<String>("Vi","Vx","Vy","x","y","Z","Z%","Ac","Rnd","Vi")

        addTable()
            addRow()
                header.forEach { s -> addElement(s) }
            commitRow()
        commitTable()
    }

    /* METHODS TO CREATE THE TABLE */
    private fun addTable(){
        table = TableLayout(this)
        table.layoutParams = linearParams
    }

    private fun addElement(element:String){
        var text = TextView(this)
        text.text = element
        text.layoutParams = rowParams
        row.addView(text)
    }

    private fun addRow(){
        row = TableRow(this)
        row.layoutParams = tableParams
    }

    private fun commitRow(){    table.addView(row)  }
    private fun commitTable(){  container.addView(table) }
}
