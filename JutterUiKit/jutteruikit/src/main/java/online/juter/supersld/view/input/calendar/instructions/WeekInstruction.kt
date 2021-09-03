package online.juter.supersld.view.input.calendar.instructions

import online.juter.supersld.common.monthEquals
import online.juter.supersld.view.input.calendar.JTCalendarAdapter
import online.juter.supersld.view.input.calendar.JTDay
import online.juter.supersld.view.input.calendar.JTWeek
import java.util.*

/**
 * Инструкция для выбора недели из из календаря.
 */
class WeekInstruction : SelectInstruction {
    override fun onSelect(adapter: JTCalendarAdapter, list: MutableList<Pair<Int, Any?>>, day: JTDay) {
        for (lineNum in list.indices) {
            val lineChanged = false
            val week = if (list[lineNum].second is JTWeek) list[lineNum].second as JTWeek else null
            if (week != null) for (dayNum in week.days.indices) week.days[dayNum]?.let {

            }
            if (lineChanged) adapter.notifyItemChanged(lineNum)
        }
    }
}