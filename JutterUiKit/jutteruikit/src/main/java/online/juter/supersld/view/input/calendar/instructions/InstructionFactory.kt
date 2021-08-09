package online.juter.supersld.view.input.calendar.instructions

import online.juter.supersld.view.input.calendar.JTCalendarView

/**
 * Фабрика инструкций для календаря.
 */
object InstructionFactory {
    fun create(mode: Int): SelectInstruction? = when(mode) {
        JTCalendarView.MODE_SELECT_ONE -> OneInstruction()
        JTCalendarView.MODE_SELECT_RANGE -> RangeInstruction()
        JTCalendarView.MODE_SELECT_WEEK -> WeekInstruction()
        else -> null
    }
}