package online.juter.supersld

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
            setData(
                mutableListOf(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), Random.nextFloat()),
                mutableListOf(
                    ContextCompat.getColor(context!!, R.color.colorGoogle1),
                    ContextCompat.getColor(context!!, R.color.colorGoogle2),
                    ContextCompat.getColor(context!!, R.color.colorGoogle3),
                    ContextCompat.getColor(context!!, R.color.colorGoogle4)
                )
            )
            refresh()
            setOnClickListener {
                refresh()
            }
        }
    }
}