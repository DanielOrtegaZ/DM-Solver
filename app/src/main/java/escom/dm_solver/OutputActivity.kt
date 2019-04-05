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

    var x = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_output)
        geneticos()
        createTable()
    }

    fun geneticos(){
        var algGen = Genetico()
        algGen.calcular()

    }

    fun createTable(){

        val table = TableLayout(this)
        table.layoutParams = linearParams

        for(i in 0..5) {

            var row = TableRow(this)
            row.layoutParams = tableParams

                for(j in 0..11) {
                    var text = TextView(this)
                    text.text = (i*j).toString()
                    text.layoutParams = rowParams
                    row.addView(text)
                }

            table.addView(row)
        }

        container.addView(table)
    }
}
