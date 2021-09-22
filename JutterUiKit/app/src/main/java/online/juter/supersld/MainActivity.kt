package online.juter.supersld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import online.juter.supersld.view.input.selectors.JTHorizontalSwitch
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(vSelector) {
            init(listOf("Первый", "Второй", "Третий и 3"), selectedIndex = 1, params = JTHorizontalSwitch.JTSwitchParams())
            onTabChanged {
                tvSelectorValue.text = listOf("Первый", "Второй", "Третий")[it]
            }
        }
    }
}