package online.juter.supersld

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import online.juter.supersld.view.data.JTDiagram
import online.juter.supersld.view.input.calendar.JTCalendarProperty
import online.juter.supersld.view.input.calendar.JTCalendarView
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(vSelector) {
            init(listOf("Первый", "Второй", "Третий и 3"))
            onTabChanged {
                tvSelectorValue.text = listOf("Первый", "Второй", "Третий")[it]
            }
        }
    }
}