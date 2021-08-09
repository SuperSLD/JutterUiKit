package online.juter.supersld.view.input.calendar

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_calendar_week.view.*
import online.juter.supersld.R
import online.juter.supersld.common.JTAbstractViewHolder

/**
 * Строка с одной неделей
 * календаря.
 */
class JTWeekHolder(
    itemView: View,
    private val property: JTCalendarProperty,
    val action: (Int, Any?)->Unit
) : JTAbstractViewHolder(itemView) {

    private val selectedDrawable = ContextCompat.getDrawable(itemView.context, property.selectedBackDrawable).apply {
        this?.setTint(property.selectedColor)
    }
    private val endDrawable = ContextCompat.getDrawable(itemView.context, R.drawable.ic_end).apply {
        this?.setTint(property.rangeSelectedColor)
    }
    private val startDrawable = ContextCompat.getDrawable(itemView.context, R.drawable.ic_start).apply {
        this?.setTint(property.rangeSelectedColor)
    }

    private val transparentColor = Color.parseColor("#00000000")

    private val daysTitle = mutableListOf(
        itemView.JTtvDay1, itemView.JTtvDay2, itemView.JTtvDay3, itemView.JTtvDay4, itemView.JTtvDay5, itemView.JTtvDay6, itemView.JTtvDay7
    )

    private val daysBack = mutableListOf(
        itemView.JTDayBack1, itemView.JTDayBack2, itemView.JTDayBack3, itemView.JTDayBack4, itemView.JTDayBack5, itemView.JTDayBack6, itemView.JTDayBack7
    )

    override fun bind(data: Any?) {
        val week = data as JTWeek
        with(itemView) {
            for (i in 0..6) {
                if (week.days[i] != null) {
                    with(daysTitle[i] as TextView) {
                        text = week.getDay(i)?.getSimpleName()
                        visibility = View.VISIBLE
                        if (week.days[i]?.inValidRange!!) {
                            setOnClickListener {
                                action(JTCalendarHolderFactory.ACTION_SELECT_DAY, week.days[i])
                            }
                            setTextColor(property.textColor)
                            daysBack[i].background = if (week.days[i]!!.isLast) endDrawable else
                                if (week.days[i]!!.isFirst) startDrawable else null
                        } else {
                            setTextColor(property.headerTextColor)
                            setOnClickListener(null)
                        }

                        if (!week.days[i]!!.selected) {
                            setTextColor(if (week.days[i]?.inValidRange!!) property.textColor else property.headerTextColor)
                            background = null
                            if (week.days[i]!!.inSelectedRange)
                                daysTitle[i].setBackgroundColor(property.rangeSelectedColor)
                            else setBackgroundColor(transparentColor)
                        } else {
                            setTextColor(if (week.days[i]?.inValidRange!!) property.selectedTextColor else property.headerTextColor)
                            background = selectedDrawable
                        }
                    }
                } else {
                    daysTitle[i].visibility = View.INVISIBLE
                    daysBack[i].background = null
                }
            }
        }
    }
}