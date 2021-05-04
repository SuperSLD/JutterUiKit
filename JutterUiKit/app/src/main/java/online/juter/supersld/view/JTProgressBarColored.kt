package online.juter.supersld.view

import android.content.Context
import android.util.AttributeSet
import online.juter.supersld.view.data.JTProgressBar

class JTProgressBarColored : JTProgressBar {
    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    /**
     * Просто проверка, потому что в первый раз при публикации
     * забыл дописать open к функции.
     */
    override fun setDefaultColor(): MutableList<Int> {
        return super.setDefaultColor()
    }
}