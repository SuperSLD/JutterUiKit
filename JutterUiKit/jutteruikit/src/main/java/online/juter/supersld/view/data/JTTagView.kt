package online.juter.supersld.view.data

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

/**
 * Отображение списка тегов.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
class JTTagView: View {
    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )
    

}