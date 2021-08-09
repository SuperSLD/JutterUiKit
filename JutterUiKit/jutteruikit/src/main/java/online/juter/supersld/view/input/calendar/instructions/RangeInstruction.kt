package online.juter.supersld.view.input.calendar.instructions

import online.juter.supersld.common.monthEquals
import online.juter.supersld.view.input.calendar.JTCalendarAdapter
import online.juter.supersld.view.input.calendar.JTDay
import online.juter.supersld.view.input.calendar.JTWeek
import java.util.*

/**
 * Инструкция для множественного выбора
 * из календаря.
 *
 * Короче написано неплохо, может даже очень нелохо.
 * В будущем надо посмотреть можно ли это сделать лучше.
 */
class RangeInstruction : SelectInstruction {
    override fun onSelect(adapter: JTCalendarAdapter, list: MutableList<Pair<Int, Any?>>, day: JTDay) {
        var range = findLastAndFirst(list)

        //сбрасываем начало и конец
        range.first?.let { it.start(false) }
        range.second?.let { it.end(false) }

        if (range.first == null) {
            day.selected = true
        } else if (range.first == range.second && day.after(range.second!!)) {
            day.selected = true
            range = Pair(range.first, day)
        } else if (range.first == range.second && day.before(range.first!!)) {
            day.selected = true
            range = Pair(day, range.second)
        } else if ((range.first != null && day.before(range.first!!)) || (range.second != null && range.second != range.first && day.after(range.second!!))) {
            range.first?.let {
                it.selected = false
                updateLine(adapter, list, it)
            }
            range.second?.let {
                it.selected = false
                updateLine(adapter, list, it)
            }
            range = Pair(day, day)
            day.selected = true
        } else if (range.first != range.second && day.after(range.first!!) && day.before(range.second!!)) {
            range.first?.let {
                it.selected = false
                updateLine(adapter, list, it)
            }
            day.selected = true
            range = Pair(day, range.second)
        }

        updateLine(adapter, list, day)
        range.first?.let {
            it.start(true)
            updateLine(adapter, list, it)
        }
        range.second?.let {
            it.end(true)
            updateLine(adapter, list, it)
        }
        if (range.second == range.first) {
            range.second?.start(false)
            range.first?.start(false)
        }

        for (lineNum in list.indices) {
            var lineChanged = false
            val week = if (list[lineNum].second is JTWeek) list[lineNum].second as JTWeek else null
            if (week != null) for (dayNum in week.days.indices) week.days[dayNum]?.let {
                // проверяем на попадание в интервал каждый день если интервал выбран
                it.inSelectedRange = if(range.first != null && it.after(range.first!!) && it.before(range.second!!)) {
                    true
                } else {
                    if (it.inSelectedRange) lineChanged = true
                    false
                }
                if (range.first != null) {
                    if (it.before(range.second!!) && it.after(range.first!!))
                        lineChanged = true
                }
            }
            if (lineChanged) adapter.notifyItemChanged(lineNum)
        }
    }

    /**
     * Поиск первого и последнего дня в выборке.
     *
     * Немного некрасиво но работает, потом обязательно
     * нужно доработать.
     *
     * @param list список элементов массива.
     * @return пара из первого и последнего дня.
     */
    private fun findLastAndFirst(list: MutableList<Pair<Int, Any?>>): Pair<JTDay?, JTDay?> {
        var first: JTDay? = null
        var end: JTDay
        for (lineNum in list.indices) {
            val week = if (list[lineNum].second is JTWeek) list[lineNum].second as JTWeek else null
            if (week != null) for (dayNum in week.days.indices) week.days[dayNum]?.let {
                if (it.selected && first == null) {
                    first = it
                } else if (it.selected) {
                    end = it
                    return Pair(first!!, end)
                }
            }
        }
        return Pair(first, first)
    }

    /**
     * Обновление линии в адаптере.
     */
    private fun updateLine(adapter: JTCalendarAdapter, list: MutableList<Pair<Int, Any?>>, day: JTDay) {
        for (lineNum in list.indices) {
            val week = if (list[lineNum].second is JTWeek) list[lineNum].second as JTWeek else null
            if (week != null) for (dayNum in week.days.indices) week.days[dayNum]?.let {
               if (it == day) adapter.notifyItemChanged(lineNum)
            }
        }
    }

    fun selectMonth(adapter: JTCalendarAdapter, list: MutableList<Pair<Int, Any?>>, month: Calendar) {
        var range = findLastAndFirst(list)

        //сбрасываем начало и конец
        range.first?.let {
            it.start(false)
            it.selected = false
            updateLine(adapter, list, it)
        }
        range.second?.let {
            it.end(false)
            it.selected = false
            updateLine(adapter, list, it)
        }

        range = Pair(null, null)
        var lastDay: JTDay? = null
        for (lineNum in list.indices) {
            val week = if (list[lineNum].second is JTWeek) list[lineNum].second as JTWeek else null
            if (week != null) for (dayNum in week.days.indices) week.days[dayNum]?.let {
                if (it.getCalendar().monthEquals(month) && range.first == null && it.inValidRange) {
                    range = Pair(it, null)
                    it.selected = true
                }
                if (it.getCalendar().monthEquals(month) && it.inValidRange)
                    lastDay = it
            }
        }
        range = Pair(range.first, lastDay)
        lastDay?.selected = true

        range.first?.let {
            it.start(true)
            updateLine(adapter, list, it)
        }
        range.second?.let {
            it.end(true)
            updateLine(adapter, list, it)
        }

        for (lineNum in list.indices) {
            var lineChanged = false
            val week = if (list[lineNum].second is JTWeek) list[lineNum].second as JTWeek else null
            if (week != null) for (dayNum in week.days.indices) week.days[dayNum]?.let {
                // проверяем на попадание в интервал каждый день если интервал выбран
                it.inSelectedRange = if(range.first != null && it.after(range.first!!) && it.before(range.second!!)) {
                    true
                } else {
                    if (it.inSelectedRange) lineChanged = true
                    false
                }
                if (range.first != null) {
                    if (it.before(range.second!!) && it.after(range.first!!))
                        lineChanged = true
                }
            }
            if (lineChanged) adapter.notifyItemChanged(lineNum)
        }
    }
}