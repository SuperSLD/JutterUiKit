package online.juter.supersld.view.input.calendar.instructions

import online.juter.supersld.view.input.calendar.JTCalendarAdapter
import online.juter.supersld.view.input.calendar.JTDay
import online.juter.supersld.view.input.calendar.JTWeek

/**
 * Инструкция одиночного выбора.
 */
class OneInstruction : SelectInstruction {
    override fun onSelect(adapter: JTCalendarAdapter, list: MutableList<Pair<Int, Any?>>, day: JTDay) {
        for (lineNum in list.indices) {
            val week = if (list[lineNum].second is JTWeek) list[lineNum].second as JTWeek else null
            if (week != null) for (dayNum in week.days.indices) {
                if (week.days[dayNum] != null && week.days[dayNum]!!.selected && day != week.days[dayNum]) {
                    week.days[dayNum]?.selected = false
                    adapter.notifyItemChanged(lineNum)
                } else if (day == week.days[dayNum]) {
                    day.selected = true
                    adapter.notifyItemChanged(lineNum)
                }
            }
        }
    }
}