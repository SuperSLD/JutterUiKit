package online.juter.supersld.view.input.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import online.juter.supersld.R
import online.juter.supersld.common.JTAbstractViewHolder

class JTCalendarHolderFactory {
    companion object {
        const val TITLE_ITEM = 0
        const val WEEK_ITEM = 1

        const val ACTION_SELECT_ALL = 0
        const val ACTION_SELECT_DAY = 1

        private var onAction = {_:Int,_:Any?->}

        fun onAction(onAction: (Int, Any?)->Unit) {
            this.onAction = onAction
        }

        fun create(type: Int, parent: ViewGroup, property: JTCalendarProperty, data: Any?): JTAbstractViewHolder? {
            when(type) {
                TITLE_ITEM -> return JTTitleHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_month, parent, false),
                    property,
                    onAction
                )
                WEEK_ITEM ->return JTWeekHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_week, parent, false),
                    property,
                    onAction
                )
            }
            return null
        }
    }
}