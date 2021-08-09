package online.juter.supersld.view.input.calendar

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import online.juter.supersld.common.JTAbstractViewHolder
import online.juter.supersld.common.dayEquals
import online.juter.supersld.view.input.calendar.instructions.RangeInstruction
import online.juter.supersld.view.input.calendar.instructions.SelectInstruction
import java.util.*

class JTCalendarAdapter(
    private val property: JTCalendarProperty
) : RecyclerView.Adapter<JTAbstractViewHolder>() {

    private var instruction: SelectInstruction? = null
    private val list = mutableListOf<Pair<Int, Any?>>()

    init {
        JTCalendarHolderFactory.onAction { id, data ->
            when(id) {
                JTCalendarHolderFactory.ACTION_SELECT_DAY -> instruction?.onSelect(this, list, data as JTDay)
                JTCalendarHolderFactory.ACTION_SELECT_ALL -> (instruction as RangeInstruction)
                    .selectMonth(this, list, data as Calendar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JTAbstractViewHolder {
        return JTCalendarHolderFactory.create(viewType, parent, property, null)!!
    }

    override fun onBindViewHolder(holderJT: JTAbstractViewHolder, position: Int) {
        holderJT.bind(list[position].second)
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return list[position].first
    }

    fun addData(instruction: SelectInstruction) {
        this.instruction = instruction
        var calendar = property.startDate.clone() as Calendar
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        for (i in 0..(property.endDate.timeInMillis - property.startDate.timeInMillis)/1000/60/60/24/30) {
            list.add(Pair(JTCalendarHolderFactory.TITLE_ITEM, Pair("${property.monthNames[calendar.get(Calendar.MONTH)]} ${calendar.get(Calendar.YEAR)}", calendar.clone())))

            val weekCalendar = calendar.clone() as Calendar
            var firstIteration = true
            weekCalendar.add(Calendar.DAY_OF_MONTH, -calendar.get(Calendar.DAY_OF_WEEK) + 2)
            while (calendar.get(Calendar.MONTH) == weekCalendar.get(Calendar.MONTH) || firstIteration) {
                firstIteration = false
                var week = JTWeek()
                for (i in 0..6) {
                    if (calendar.get(Calendar.MONTH) == weekCalendar.get(Calendar.MONTH)) {

                        week.put(
                            JTDay(
                                getDayStr(weekCalendar),
                                false,
                                weekCalendar.after(property.startDate) && weekCalendar.before(property.endDate)
                                        || weekCalendar.dayEquals(property.startDate) || weekCalendar.dayEquals(property.endDate)
                            )
                        )
                    } else {
                        //week.put(JTDayNull(getDayStr(weekCalendar)))
                        week.put(null)
                    }
                    weekCalendar.add(Calendar.DAY_OF_MONTH, 1)
                }
                list.add(Pair(JTCalendarHolderFactory.WEEK_ITEM, week))
            }
            calendar.add(Calendar.MONTH, 1)
        }
    }

    fun getDayStr(weekCalendar: Calendar): String {
        val day = weekCalendar.get(Calendar.DAY_OF_MONTH)
        val month = weekCalendar.get(Calendar.MONTH) + 1
        val year = weekCalendar.get(Calendar.YEAR)
        return "${if(day < 10) "0$day" else "$day"}.${if(month < 10) "0$month" else "$month"}.$year"
    }

    fun getOne(): String? {
        return getRange().first
    }

    fun getRange(): Pair<String?, String?> {
        var first: JTDay? = null
        var end: JTDay
        for (lineNum in list.indices) {
            val week = if (list[lineNum].second is JTWeek) list[lineNum].second as JTWeek else null
            if (week != null) for (dayNum in week.days.indices) week.days[dayNum]?.let {
                if (it.selected && first == null) {
                    first = it
                } else if (it.selected) {
                    end = it
                    return Pair(first?.name, end.name)
                }
            }
        }
        return Pair(first?.name, first?.name)
    }
}