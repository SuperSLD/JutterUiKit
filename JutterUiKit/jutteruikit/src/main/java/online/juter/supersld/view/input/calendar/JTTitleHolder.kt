package online.juter.supersld.view.input.calendar

import android.view.View
import kotlinx.android.synthetic.main.item_calendar_month.view.*
import online.juter.supersld.common.JTAbstractViewHolder
import java.util.*

class JTTitleHolder(
    itemView: View,
    private val property: JTCalendarProperty,
    val action: (Int, Any?)->Unit
) : JTAbstractViewHolder(itemView) {

    init {
        with(itemView) {
            tvMonthTitle.setTextColor(property.textColor)
            with(tvMonthEnd) {
                setTextColor(property.headerTextColor)
                text = property.textAllButton
                if (property.selectMode != JTCalendarView.MODE_SELECT_RANGE) visibility = View.GONE
            }
        }
    }

    override fun bind(data: Any?) {
        val monthData = data as Pair<*, *>
        with(itemView) {
            tvMonthTitle.text = monthData.first as String
            tvMonthEnd.setOnClickListener {
                action(JTCalendarHolderFactory.ACTION_SELECT_ALL, monthData.second)
            }
        }
    }
}