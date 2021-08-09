package online.juter.supersld.common

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.dayEquals(other: Any?): Boolean =
    if (other is Calendar) this.get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR)
            && this.get(Calendar.YEAR) == other.get(Calendar.YEAR) else false

fun Calendar.monthEquals(other: Any?): Boolean =
    if (other is Calendar) this.get(Calendar.MONTH) == other.get(Calendar.MONTH)
            && this.get(Calendar.YEAR) == other.get(Calendar.YEAR) else false

/**
 * Парсинг календаря из строки.
 * Полезная функция, везде где идет получение календаря.
 * @param format формат даты ("dd.MM.yyyy")
 */
fun String.parseCalendarByFormat(format: String): Calendar {
    val calendar = Calendar.getInstance()
    val sdf =
        SimpleDateFormat(format, Locale.ENGLISH)
    calendar.time = sdf.parse(this)

    return calendar
}