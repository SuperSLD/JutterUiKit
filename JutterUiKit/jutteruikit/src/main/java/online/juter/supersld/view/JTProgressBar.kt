package online.juter.supersld.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import kotlin.math.cos
import kotlin.math.sin

/**
 * Круглый лоадер с изменяющимся
 * после каждого оборота цветом.
 *
 * <p>
 *     Отличный способ заменить обычный
 *     ProgressBar, чем-то более оживленным.
 * </p>
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
open class JTProgressBar: View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    private var mColors = mutableListOf<Int>()
    private var mFirstAngle = 0f
    private var mSecondAngle = 30f
    private var mCurrentColorIndex = 0
    private var mCurrentColor = 0
    private var mFirstAnimator : ValueAnimator? = null
    private var mSecondAnimator : ValueAnimator? = null
    private var mColorAnimator: ValueAnimator? = null
    private var mLastFirstAngle = mFirstAngle
    private var mLastSecondAngle = mSecondAngle

    private var mWithPart = 0.13f
    private var mDuration = 1000L
    private val mRotateAngle = 220f

    init {
        mColors = setDefaultColor()
        if (mColors.size == 0) throw IllegalArgumentException("setDefaultColor() return empty list")
        mCurrentColor = mColors[0]
        mFirstAnimator = ValueAnimator.ofFloat(0f, mRotateAngle)
        with(mFirstAnimator!!) {
            duration = mDuration
            repeatCount = 0
            interpolator = LinearInterpolator()
            addUpdateListener { a ->
                mFirstAngle = mLastFirstAngle + a.animatedValue as Float
                mSecondAngle = mLastSecondAngle + a.animatedValue as Float * 2
                correctAngle()
                invalidate()
            }
            doOnEnd {
                mLastFirstAngle = mFirstAngle
                mLastSecondAngle = mSecondAngle
                mSecondAnimator?.start()
            }
        }

        mSecondAnimator = ValueAnimator.ofFloat(0f, mRotateAngle)
        with(mSecondAnimator!!) {
            duration = mDuration
            repeatCount = 0
            interpolator = LinearInterpolator()
            addUpdateListener { a ->
                mFirstAngle = mLastFirstAngle + a.animatedValue as Float * 2
                mSecondAngle = mLastSecondAngle + a.animatedValue as Float
                correctAngle()
                invalidate()
            }
            doOnEnd {
                mCurrentColorIndex++
                if (mCurrentColorIndex == mColors.size) mCurrentColorIndex = 0

                mColorAnimator = ValueAnimator.ofArgb(
                        mColors[if (mCurrentColorIndex - 1 < 0) mColors.size - 1 else mCurrentColorIndex - 1],
                        mColors[mCurrentColorIndex]
                )
                with(mColorAnimator!!) {
                    duration = mDuration
                    repeatCount = 0
                    addUpdateListener { a -> mCurrentColor = a.animatedValue as Int }
                    start()
                }

                mLastFirstAngle = mFirstAngle
                mLastSecondAngle = mSecondAngle
                mFirstAnimator?.start()
            }
        }

        mFirstAnimator?.start()
    }

    /**
     * Приводим углы к 360.
     */
    private fun correctAngle() {
        mFirstAngle %= 360
        mSecondAngle %= 360
    }

    /**
     * Выбор скорости вращения ProgressBar.
     * @param duration время одного этапа прокрутки.
     */
    fun setDuration(duration: Long) {
        mDuration = duration
    }

    /**
     * Выбор толщины ProgressBar.
     * @param width процент толщины от высоты.
     */
    fun setWidthPart(width: Float) {
        mWithPart = width
    }

    /**
     * Установка цветов для прогресбара.
     * @param colors список цветов.
     */
    fun setColors(colors: MutableList<Int>) {
        if (colors.size > 0) {
            mColors = colors
        }
    }

    /**
     * Установка дефолтных цветов.
     * Метод можно переопределить и изменить
     * дефолтные цвета, чтобы не делать это
     * при каждом использовании.
     *
     * @return список цветов, который не может быть пустым.
     */
    open protected fun setDefaultColor() : MutableList<Int> {
        return mutableListOf(
                Color.parseColor("#EA4335"),
                Color.parseColor("#FBBC05"),
                Color.parseColor("#34A853"),
                Color.parseColor("#4285F4")
        )
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val mPaint = Paint()
        val h = measuredHeight
        val w = measuredWidth
        mPaint.flags = Paint.ANTI_ALIAS_FLAG

        mPaint.color = mCurrentColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = h * mWithPart
        canvas!!.drawArc(
                w / 2 - h / 2.toFloat() + h*mWithPart/2,
                0f  + h*mWithPart/2,
                w / 2 + h / 2.toFloat() - h*mWithPart/2,
                h.toFloat() - h*mWithPart/2,
                mFirstAngle, secondAngleToDelta() - mFirstAngle,
                false,
                mPaint
        )

        var lastAngle = 0f
        mPaint.style = Paint.Style.FILL
        for (angle in mutableListOf(mFirstAngle, secondAngleToDelta() - mFirstAngle)) {
            canvas.drawCircle((cos(Math.toRadians(lastAngle + angle.toDouble())) * (h / 2 - h*mWithPart/2)).toFloat() + w / 2,
                    (sin(Math.toRadians(lastAngle + angle.toDouble())) * (h / 2 - h*mWithPart/2)).toFloat() + h / 2,
                    h*mWithPart/2, mPaint)
            lastAngle += angle
        }
    }

    /**
     * Приводим угол к виду из которого с ним
     * удобнее работать для прибавления углов.
     *
     * @return mSecondAngle в интервале от 0 дл 360 если
     * он больше первого угла и +360 если он меньше первого угла.
     */
    private fun secondAngleToDelta() = if (mSecondAngle < mFirstAngle) mSecondAngle + 360 else mSecondAngle
}