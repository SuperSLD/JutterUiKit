package online.juter.supersld.view.input.calendar.instructions

import online.juter.supersld.view.input.calendar.JTCalendarAdapter
import online.juter.supersld.view.input.calendar.JTDay

/**
 * Инструкция для выбора дней в календарею.
 */
interface SelectInstruction {
    fun onSelect(adapter: JTCalendarAdapter, list: MutableList<Pair<Int, Any?>>, day: JTDay)
}