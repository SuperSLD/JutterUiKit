package online.juter.supersld

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import online.juter.supersld.view.JTDiagram
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(vDiagram) {
            setCenterText("Touch to restart")
            setCenterSubText("Animation")
            setColorText(
                ContextCompat.getColor(context!!, R.color.colorTextHint),
                ContextCompat.getColor(context!!, R.color.colorTextHint)
            )
            recreateDiagramData(this)
            refresh()
            setOnClickListener {
                refresh()
                recreateDiagramData(this)
            }
        }

        with(vProgressFirst) {
            setEmptyColor(Color.parseColor("#E6EBF0"))
            setMaxProgress(200)
            setCenterTextFormat("%.0f%%")
            setProgress(Random.nextInt(0, 200))
            setOnClickListener {
                setProgress(Random.nextInt(0, 200))
            }
        }

        with(vProgressSecond) {
            setMaxProgress(200)
            setProgress(143)
            setColorText(ContextCompat.getColor(baseContext, R.color.colorGoogle3))
            setColor(ContextCompat.getColor(baseContext, R.color.colorGoogle3))
            fixProgress(false)
        }
    }

    private fun recreateDiagramData(diagram: JTDiagram) {
        diagram.setData(
                mutableListOf(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), Random.nextFloat()),
                mutableListOf(
                        ContextCompat.getColor(baseContext, R.color.colorGoogle1),
                        ContextCompat.getColor(baseContext, R.color.colorGoogle2),
                        ContextCompat.getColor(baseContext, R.color.colorGoogle3),
                        ContextCompat.getColor(baseContext, R.color.colorGoogle4)
                )
        )
    }
}