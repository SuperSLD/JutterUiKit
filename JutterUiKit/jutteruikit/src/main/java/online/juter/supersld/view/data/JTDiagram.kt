package online.juter.supersld.view.data

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

/**
 * Отображение круговой-кольцевой
 * диаграммы, с текстом в центре.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
class JTDiagram :View {
    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    private var mCenterText = ""
    private var mData: MutableList<Float>? = null
    private var mColors: MutableList<Int>? = null
    private var mDataSum = 0f
    private var mCenterSubText = ""
    private var mWithPart = 0.1f
    private var mMaxAngle = 0f
    private var mMaxAngleAnimator: ValueAnimator? = null
    private var mDuration = 4000L

    private var mColorText = 0
    private var mColorSubText = 0
    var mLastAngle = -90f

    /**
     * Установка текста в центре диаграммы
     * @param text текст
     */
    fun setCenterText(text: String) {
        mCenterText = text
    }

    /**
     * Установка продолжителькости анимации.
     * @param duration текст
     */
    fun setDuration(duration: Long) {
        mDuration = duration
    }

    /**
     * Установка цветов надписей.
     * @param colorText цвет 1
     * @param colorSubText цвет 2
     */
    fun setColorText(colorText: Int, colorSubText: Int) {
        mColorSubText = colorSubText
        mColorText = colorText
    }

    /**
     * Установка текста под основной надписью.
     * @param text текст.
     */
    fun setCenterSubText(text: String) {
        mCenterSubText = text
    }

    /**
     * Установка данных для отобрадения и цветов которыми данные будут отображаться.
     * @param data данные
     */
    fun setData(
        data: MutableList<Float>,
        colors: MutableList<Int>
    ) {
        if (data.size > colors.size) throw IndexOutOfBoundsException("data.length > colors.length")
        mColors = colors
        mData = data
        mDataSum = 0f
        for (s in data) {
            mDataSum += s
        }
    }

    /**
     * Выбор толщины ProgressBar.
     * @param width процент толщины от высоты.
     */
    fun setWidthPart(width: Float) {
        mWithPart = width
    }

    /**
     * Обновление состояния, по умолчанию
     * максимальный угол равен нулю, но при
     * старте этого методаю, в зависимости от параметра,
     * выбирается правильный максимальный угол.
     *
     * @param animated если false то диаграмма отображается
     *                 без анимации. (true по умолчанию)
     */
    fun refresh(animated: Boolean = true) {
        if (animated) {
            mMaxAngleAnimator = ValueAnimator.ofFloat(0f, 360f)
            with(mMaxAngleAnimator!!) {
                addUpdateListener {
                    mMaxAngle = it.animatedValue as Float
                    invalidate()
                }
                duration = mDuration
                start()
            }
        } else {
            mMaxAngle = 360f
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val mPaint = Paint()
        val h = measuredHeight
        val w = measuredWidth
        mPaint.flags = Paint.ANTI_ALIAS_FLAG

        mPaint.strokeWidth = h * mWithPart
        // диаграмма
        if (mData == null) {
            mPaint.style = Paint.Style.STROKE
            mPaint.color = Color.parseColor("#909DA9")
            canvas?.drawCircle(w / 2.toFloat(), h / 2.toFloat(), h / 2.toFloat() - h*mWithPart/2, mPaint)
            mPaint.style = Paint.Style.FILL
        } else {
            mPaint.style = Paint.Style.FILL
            mPaint.color = mColors!![0]
            canvas!!.drawCircle(
                0f * (h / 2 - h / 20f) + w / 2,
                -1f * (h / 2 - h / 20f) + h / 2,
                h*mWithPart/2,
                mPaint
            )
            mPaint.style = Paint.Style.STROKE


            mLastAngle = -90f

            for (i in mData!!.indices) {
                var angle = mData!![i] * mMaxAngle / mDataSum
                mPaint.color = mColors!![i]
                canvas.drawArc(
                        w / 2 - h / 2.toFloat() + h*mWithPart/2,
                        0f + h*mWithPart/2,
                        w / 2 + h / 2.toFloat() - h*mWithPart/2,
                        h.toFloat() - h*mWithPart/2,
                        mLastAngle,
                        angle,
                        false,
                        mPaint
                )
                mLastAngle += angle
            }
            mLastAngle = -90f
            mPaint.style = Paint.Style.FILL
            for (i in mData!!.indices) {
                var angle = mData!![i] * mMaxAngle / mDataSum
                mPaint.color = mColors!![i]
                canvas!!.drawCircle(
                    (cos(Math.toRadians(mLastAngle + angle.toDouble())) * (h / 2 - h / 20)).toFloat() + w / 2,
                    (sin(Math.toRadians(mLastAngle + angle.toDouble())) * (h / 2 - h / 20)).toFloat() + h / 2,
                    h*mWithPart/2,
                    mPaint
                )
                mLastAngle += angle
            }
        }


        // надпись в центре
        mPaint.color = mColorText
        mPaint.textSize = h / 10.toFloat()
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.isFakeBoldText = true
        canvas?.drawText(mCenterText, w / 2.toFloat(), h / 2.toFloat(), mPaint)
        mPaint.textSize = h / 15.toFloat()
        mPaint.isFakeBoldText = false
        mPaint.color = mColorSubText
        canvas?.drawText(mCenterSubText, w / 2.toFloat(), h / 2 + h / 10.toFloat(), mPaint)
    }
}