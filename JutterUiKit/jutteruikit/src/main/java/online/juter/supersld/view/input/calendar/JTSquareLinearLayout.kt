package online.juter.supersld.view.input.calendar

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Квадратный линейный контейнер.
 */
class JTSquareLinearLayout : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}