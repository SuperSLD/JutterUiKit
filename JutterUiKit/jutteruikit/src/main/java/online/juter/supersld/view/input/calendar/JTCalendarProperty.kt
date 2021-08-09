package online.juter.supersld.view.input.calendar

import android.graphics.Color
import online.juter.supersld.R
import java.util.*

/**
 * Настройки календаря.
 */
open class JTCalendarProperty(
    // шрифт
    val font: Int? = null,
    // фон заголовка с неделямт
    val headerColor: Int = Color.parseColor("#F2F2F2"),
    // цвет текста заголовка с неделями
    val headerTextColor: Int = Color.parseColor("#979797"),
    // основной цвет текста
    val textColor: Int = Color.parseColor("#000000"),
    // цвет текста в выбранных элементах
    val selectedTextColor: Int = Color.parseColor("#FFFFFF"),
    // цвет выбранного элемегта
    val selectedColor: Int = Color.parseColor("#DBDBDB"),
    // цвет между выбррынными элементами
    val rangeSelectedColor: Int = Color.parseColor("#EFEFF3"),
    // картинка для выбранного элемента
    val selectedBackDrawable: Int = R.drawable.shape_calendar_selector,

    // названия дней
    val dayNames: MutableList<String> = mutableListOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"),
    // названия месяцев
    val monthNames: MutableList<String> = mutableListOf(
        "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    ),

    // стартовая дата
    val startDate: Calendar = Calendar.getInstance().apply { add(Calendar.MONTH, -1) },
    // конечная дата
    val endDate: Calendar = Calendar.getInstance().apply { add(Calendar.YEAR, 1) },

    // тип выбора элементов
    val selectMode: Int = JTCalendarView.MODE_SELECT_RANGE,
    // показать текущий месяц
    val showLastMonth: Boolean = true,
    // текст кнопки "выбрать все"
    val textAllButton: String = "Весь"
)