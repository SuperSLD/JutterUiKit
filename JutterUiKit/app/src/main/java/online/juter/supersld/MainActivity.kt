package online.juter.supersld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import online.juter.supersld.view.input.form.JTForm
import online.juter.supersld.view.input.form.JTFormPage
import online.juter.supersld.view.input.form.lines.*
import online.juter.supersld.view.input.form.lines.TextInputLine.Companion.INTEGER
import java.util.*
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(vSwitch) {
            init(mutableListOf("Первый таб", "Второй таб", "Третий таб"))
        }
    }

}