package online.juter.supersld.view.input.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_jt_calendar.view.*
import online.juter.supersld.R
import online.juter.supersld.view.input.calendar.instructions.InstructionFactory
import java.lang.IllegalArgumentException

/**
 * Календарь с миллионом функций. (которые когда-то будут)
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
open class JTCalendarView : RelativeLayout {

    companion object {
        const val MODE_SELECT_ONE = 0
        const val MODE_SELECT_WEEK = 1
        const val MODE_SELECT_RANGE = 2
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    private var mAdapter: JTCalendarAdapter? = null
    private val mHeaderCelList = mutableListOf<View>()

    init {
        View.inflate(context, R.layout.view_jt_calendar, this)
        mHeaderCelList.addAll(mutableListOf(
            JTtvH1, JTtvH2, JTtvH3, JTtvH4, JTtvH5, JTtvH6, JTtvH7
        ))
    }

    /**
     * Инициализация календаря.
     * @param property параметры для кастомизации календаря.
     */
    fun init(property: JTCalendarProperty) {
        if (property.dayNames.size < 7) throw IllegalArgumentException("day names size < 7")
        if (property.monthNames.size < 12) throw IllegalArgumentException("month names size < 12")

        JTvgHeader.setBackgroundColor(property.headerColor)
        for (i in mHeaderCelList.indices) {
            with(mHeaderCelList[i] as TextView) {
                setTextColor(property.headerTextColor)
                text = property.dayNames[i]
            }
        }
        val instruction = InstructionFactory.create(property.selectMode)
            ?: throw IllegalArgumentException("select mode invalid, please select correct value (example: JTCalendarView.MODE_SELECT_ONE)")

        mAdapter = JTCalendarAdapter(property)
        mAdapter?.addData(instruction)
        with(JTrvCalendar) {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
        if (property.showLastMonth) {
            JTrvCalendar.layoutManager?.scrollToPosition(mAdapter!!.itemCount - 1)
        }
    }

    fun getOne(): String? = mAdapter!!.getOne()

    fun getRange(): Pair<String?, String?> = mAdapter!!.getRange()

}