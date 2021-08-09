package online.juter.supersld.view.input.calendar

import online.juter.supersld.common.parseCalendarByFormat
import java.util.*
import javax.sql.StatementEvent

class JTWeek {
    var days = mutableListOf<JTDay?>()

    fun put(day: JTDay?) {
        days.add(day)
    }

    fun getDay(position: Int): JTDay? {
        return days[position]
    }
}

open class JTDay (
    val name: String,
    var selected: Boolean,
    val inValidRange: Boolean,
    var inSelectedRange: Boolean = false
) {

    var isFirst = false
    var isLast = false

    private var calendar: Calendar? = null
    fun getCalendar(): Calendar  {
        if (calendar == null) calendar = name.parseCalendarByFormat("dd.MM.yyyy")
        return calendar as Calendar
    }

    fun getSimpleName(): String {
        val simpleName = name.split(".")[0]
        return if (simpleName.first() == '0') simpleName.substring(1) else simpleName
    }

    open override fun equals(other: Any?): Boolean {
        return if (other is JTDay) other.name == this.name else false
    }

    /**
     * Перекидываем before and after на внутренний календарь.
     */
    fun after(other: JTDay) = getCalendar().after(other.getCalendar())
    fun before(other: JTDay) = getCalendar().before(other.getCalendar())

    fun end(end: Boolean) {
        isFirst = false
        this.isLast = end
    }
    fun start(start: Boolean) {
        isLast = false
        this.isFirst = start
    }

}

class JTDayNull(date:String) : JTDay(date, false, false) {
    override fun equals(other: Any?): Boolean {
        return other == null
    }
}