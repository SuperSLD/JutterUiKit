package online.juter.supersld.view.input.selectors

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import online.juter.supersld.common.px
import java.time.Duration


/**
 * Горизонтальный текстовый селектор.
 * Выбор одного таба из нескольких.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
class JTHorizontalSwitch : LinearLayout {

    companion object {
        private const val VIRTUAL_SIZE = 2000
    }

    constructor(context: Context) : super(context) {
        setWillNotDraw(false)
    }
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        setWillNotDraw(false)
    }
    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    ) {
        setWillNotDraw(false)
    }

    private var mParams: JTSwitchParams? = null
    private var mPosition: Int = 0
    private var mTabs: List<String> = listOf()
    private var mSelectorX = 0
    private var mAnimation: ValueAnimator? = null
    private var mCallback: ((Int)->Unit)? = null

    private var mDuration = 300L

    init {
        orientation = HORIZONTAL
    }

    /**
     * Изменение продолжительности анимации.
     * @param duration длинна анимации в мс.
     */
    fun setDuration(duration: Long) {
        this.mDuration = duration
    }

    /**
     * Инициализация селектора.
     *
     * @param tabs список табов которые можно выбирать.
     * @param params параметры для визуализации.
     */
    fun init(tabs: List<String>, selectedIndex: Int = 0, params: JTSwitchParams = JTSwitchParams()) {
        mTabs = tabs
        mParams = params
        mPosition = selectedIndex

        for ((i, tabText) in tabs.withIndex()) {
            val tabTextView = TextView(context)
            tabTextView.text = tabText
            tabTextView.setTextColor(if (i == selectedIndex) params.getTextColorSelected() else params.getTextColor())
            tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, params.fontSize.toFloat())
            tabTextView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f)
            tabTextView.gravity = Gravity.CENTER
            tabTextView.setPadding(params.fontPadding, params.fontPadding, params.fontPadding, params.fontPadding)
            tabTextView.setOnClickListener {
                select(i)
            }

            if (params.fontFamily != null) {
                val typeface = ResourcesCompat.getFont(context, params.fontFamily)
                tabTextView.typeface = typeface
            }

            this.addView(tabTextView)
        }
    }

    /**
     * Установка выбранного индекса.
     * @param index индекс таба.
     */
    fun select(index: Int) {
        if (mAnimation?.isRunning != true && index != mPosition) {
            val newTv = this[index] as TextView
            val oldTv = this[mPosition] as TextView
            val firstColorAnimator = ValueAnimator.ofArgb(
                mParams!!.getTextColor(),
                mParams!!.getTextColorSelected()
            )
            with(firstColorAnimator) {
                duration = mDuration
                repeatCount = 0
                addUpdateListener { a -> newTv.setTextColor(a.animatedValue as Int) }
                start()
            }
            oldTv.setTextColor(mParams!!.getTextColor())
            val secondColorAnimator = ValueAnimator.ofArgb(
                mParams!!.getTextColorSelected(),
                mParams!!.getTextColor()
            )
            with(secondColorAnimator) {
                duration = mDuration
                repeatCount = 0
                addUpdateListener { a -> oldTv.setTextColor(a.animatedValue as Int) }
                start()
            }
            mAnimation = ValueAnimator.ofInt(mSelectorX, (VIRTUAL_SIZE/mTabs.size) * index)
            with(mAnimation!!) {
                duration = mDuration
                repeatCount = 0
                addUpdateListener { a ->
                    mSelectorX = a.animatedValue as Int
                    invalidate()
                }
                doOnEnd {
                    mCallback?.let { c -> c(index) }
                }
                start()
            }

            mPosition = index
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val mPaint = Paint()
        val h = measuredHeight
        val w = measuredWidth
        mPaint.flags = Paint.ANTI_ALIAS_FLAG

        mPaint.color = mParams!!.getBackColor()
        val back = roundedRect(0F, 0F, w.toFloat(), h.toFloat(), mParams!!.corners.toFloat().px, mParams!!.corners.toFloat().px, false)
        canvas?.drawPath(back, mPaint)

        val sectionW = w/mTabs.size
        mPaint.color = mParams!!.getBackColorSelected()
        val selector = roundedRect(virtualXtoCurrent(mSelectorX), 0F, virtualXtoCurrent(mSelectorX) + sectionW, h.toFloat(), mParams!!.corners.toFloat().px, mParams!!.corners.toFloat().px, false)
        canvas?.drawPath(selector, mPaint)

        super.onDraw(canvas)
    }

    /**
     * Установка слушателя на смену
     * таба в селекторе.
     */
    fun onTabChanged(listener: (Int)->Unit) {
        this.mCallback = listener
    }

    data class JTSwitchParams(
        val textColorDefault: String = "#9696A1",
        val textColorSelected: String = "#FFFFFF",

        val backColor: String = "#E9E9F5",
        val backColorSelected: String = "#008F4C",

        val fontFamily: Int? = null,
        val fontSize: Int = 14,
        val fontPadding: Int = 16,

        val corners: Int = 16
    ) {
        fun getTextColor() = Color.parseColor(textColorDefault)
        fun getTextColorSelected() = Color.parseColor(textColorSelected)

        fun getBackColor() = Color.parseColor(backColor)
        fun getBackColorSelected() = Color.parseColor(backColorSelected)
    }

    private fun roundedRect(
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        rx: Float,
        ry: Float,
        conformToOriginalPost: Boolean
    ): Path {
        var rx = rx
        var ry = ry
        val path = Path()
        if (rx < 0) rx = 0f
        if (ry < 0) ry = 0f
        val width = right - left
        val height = bottom - top
        if (rx > width / 2) rx = width / 2
        if (ry > height / 2) ry = height / 2
        val widthMinusCorners = width - 2 * rx
        val heightMinusCorners = height - 2 * ry
        path.moveTo(right, top + ry)
        path.rQuadTo(0f, -ry, -rx, -ry) //top-right corner
        path.rLineTo(-widthMinusCorners, 0f)
        path.rQuadTo(-rx, 0f, -rx, ry) //top-left corner
        path.rLineTo(0f, heightMinusCorners)
        if (conformToOriginalPost) {
            path.rLineTo(0f, ry)
            path.rLineTo(width, 0f)
            path.rLineTo(0f, -ry)
        } else {
            path.rQuadTo(0f, ry, rx, ry) //bottom-left corner
            path.rLineTo(widthMinusCorners, 0f)
            path.rQuadTo(rx, 0f, rx, -ry) //bottom-right corner
        }
        path.rLineTo(0f, -heightMinusCorners)
        path.close() //Given close, last lineto can be removed.
        return path
    }

    private fun virtualXtoCurrent(virtual: Int) = (virtual / VIRTUAL_SIZE.toFloat()) * measuredWidth
}