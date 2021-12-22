package online.juter.supersld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import online.juter.supersld.view.input.form.JTForm
import online.juter.supersld.view.input.form.JTFormPage
import online.juter.supersld.view.input.form.lines.*
import online.juter.supersld.view.input.form.lines.TextInputLine.Companion.INTEGER
import online.juter.supersld.view.input.selectors.JTHorizontalSwitch
import org.w3c.dom.Text
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(vForm) {
            init(
                createForm(),
                supportFragmentManager
            )
        }
    }

    private fun createForm() = JTForm(
        name = "test_form",
        finishText = "Завершить",
        pages = mutableListOf(
            JTFormPage(
                lines = mutableListOf(
                    TextLine("Текстовая линия"),
                    SolidTextLine("Плашка с инфой"),
                    SolidTextLine("И еще одна плашка с инфой, но в ней намного больше текста"),
                    TextLine("Текст после плашки с инфой"),
                    TextInputLine("et1", "Ввод текста", mandatory = true),
                    TextInputLine("et2", "Введенный текст", value = "Введенный заранее текст", mandatory = false),
                    TextInputLine("et3", "Введенный текст", value = "228", inputType = INTEGER),
                    RadioLine(
                        id = "rg",
                        title = "Выбор из нескольких вариантов",
                        list = mutableListOf(RadioItem("it1", "Первый вариант"), RadioItem("it2", "Второй вариант"))
                    ),
                    CheckBoxLine("cb1", text = "Все работает?")
                ),
                buttonText = "Далее"
            ),
            JTFormPage(
                lines = mutableListOf(
                    TextLine("Текстовая линия"),
                    TextInputLine("et1", "Ввод текста", mandatory = true),
                    TextInputLine("et228", "Введенный текст", value = "Введенный заранее текст + 228", mandatory = false),
                    RadioLine(
                        id = "rg",
                        title = "Выбор из нескольких вариантов",
                        list = mutableListOf(RadioItem("it1", "Первый вариант"), RadioItem("it2", "Второй вариант"))
                    ),
                    CheckBoxLine("cb1", text = "Все работает?")
                ),
                buttonText = "Далее"
            ),
            JTFormPage(
                lines = mutableListOf(
                    TextLine("Текстовая линия"),
                    TextInputLine("et1", "Ввод текста", mandatory = true),
                    TextInputLine("et2", "Введенный текст", mandatory = false),
                    RadioLine(
                        id = "rg",
                        title = "Выбор из нескольких вариантов",
                        list = mutableListOf(RadioItem("it1", "Первый вариант"), RadioItem("it2", "Второй вариант"))
                    ),
                    CheckBoxLine("cb1", text = "Все работает?")
                ),
                buttonText = "Далее"
            )
        )
    )
}