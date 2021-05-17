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
 * Отображение диаграммы с заполнением
 * в какой-либо величине.
 * Например 34% из 100%.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
class JTValueViewer: View {
    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    private var mProgress = 0f
    private var mMaxProgress = 100f
    private var mCenterTextFormat = "%.2f"

    private var mFixProgress = true
    private var mTypeFaceId: Int? = null

    private var mColor = Color.parseColor("#EA4335")
    private var mColorBack: Int? = null
    private var mColorText = Color.parseColor("#EA4335")

    private var mDuration = 4000L
    private var mAnimator: ValueAnimator? = null
    private var mWithPart = 0.1f
    private var mFontPart = 0.2f

    /**
     * Установка текста в центре диаграммы
     * @param textFormat текст
     */
    fun setCenterTextFormat(textFormat: String) {
        mCenterTextFormat = textFormat
    }

    /**
     * Установка шрифта для текста в центре диаграммы
     * @param typeFace id шрифта.
     */
    fun setTypeFace(typeFace: Int) {
        mTypeFaceId = typeFace
    }


    /**
     * Установка продолжителькости анимации.
     * @param duration текст
     */
    fun setDuration(duration: Long) {
        mDuration = duration
    }

    /**
     * Установка цвета надписи.
     * @param colorText цвет 1
     */
    fun setColorText(colorText: Int) {
        mColorText = colorText
    }

    /**
     * Выбор толщины ProgressBar.
     * @param width процент толщины от высоты.
     */
    fun setWidthPart(width: Float) {
        mWithPart = width
    }

    /**
     * Выбор размера щрифта.
     * @param width процент толщины от высоты.
     */
    fun setFontPart(width: Float) {
        mFontPart = width
    }

    /**
     * Установка цыета незаполненной части.
     * @param color цвет которым будет заполнена пустая часть.
     */
    fun setEmptyColor(color: Int) {
        mColorBack = color
    }

    /**
     * Установка соновного цвета.
     * @param color цвет основной части.
     */
    fun setColor(color: Int) {
        mColor = color
    }

    /**
     * Установка максимального значения.
     * @param progress полное значение прогресса.
     */
    fun setMaxProgress(progress: Float) {
        mMaxProgress = progress
        invalidate()
    }

    /**
     * Если [mFixProgress] true, то края скругляются
     * иначе края будут прямоугольными.
     */
    fun fixProgress(fix: Boolean) {
        mFixProgress = fix
    }

    /**
     * Установка максимального значения.
     * @param progress полное значение прогресса.
     */
    fun setMaxProgress(progress: Int) {
        setMaxProgress(progress.toFloat())
    }

    /**
     * Установка значения в прогресс
     * @param progress полное значение прогресса.
     */
    fun setProgress(progress: Float, animated: Boolean = true) {
        if (animated) {
            mAnimator = ValueAnimator.ofFloat(0f, progress)
            with(mAnimator!!) {
                duration = ((progress / mMaxProgress) * mDuration).toLong()
                addUpdateListener {
                    mProgress = it.animatedValue as Float
                    invalidate()
                }
                doOnEnd {
                    if (mProgress > mMaxProgress) mProgress = 0f
                }
                start()
            }
        } else {
            mProgress = progress
            if (mProgress > mMaxProgress) mProgress = 0f
            invalidate()
        }
    }

    /**
     * Доавление значения в прогресс
     * @param progress значение прогресса
     *        которое будет добавлено к текущему.
     */
    fun addProgress(progress: Float, animated: Boolean = true) {
        if (animated) {
            mAnimator = ValueAnimator.ofFloat(mProgress, mProgress + progress)
            with(mAnimator!!) {
                duration = ((progress / mMaxProgress) * mDuration).toLong()
                addUpdateListener {
                    mProgress = it.animatedValue as Float
                    invalidate()
                }
                doOnEnd {
                    if (mProgress > mMaxProgress) mProgress = 0f
                }
                start()
            }
        } else {
            mProgress += progress
            if (mProgress > mMaxProgress) mProgress = 0f
            invalidate()
        }
    }

    /**
     * Установка значения в прогресс
     * @param progress полное значение прогресса.
     */
    fun setProgress(progress: Int, animated: Boolean = true) {
        setProgress(progress.toFloat(), animated)
    }

    /**
     * Доавление значения в прогресс
     * @param progress значение прогресса
     *        которое будет добавлено к текущему.
     */
    fun addProgress(progress: Int, animated: Boolean = true) {
        addProgress(progress.toFloat(), animated)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val mPaint = Paint()
        val h = measuredHeight
        val w = measuredWidth
        mPaint.flags = Paint.ANTI_ALIAS_FLAG

        mPaint.strokeWidth = h * mWithPart

        mPaint.style = Paint.Style.STROKE
        if (mColorBack != null) {
            mPaint.color = mColorBack!!
            canvas?.drawCircle(w / 2.toFloat(), h / 2.toFloat(), h / 2.toFloat() - h*mWithPart/2, mPaint)
        }
        mPaint.color = mColor
        canvas?.drawArc(
            w / 2 - h / 2.toFloat() + h*mWithPart/2,
            0f + h*mWithPart/2,
            w / 2 + h / 2.toFloat() - h*mWithPart/2,
            h.toFloat() - h*mWithPart/2,
            -90f,
            mProgress * 360 / mMaxProgress,
            false,
            mPaint
        )

        mPaint.style = Paint.Style.FILL

        if (mFixProgress) {
            var lastAngle = -90f
            for (angle in mutableListOf(0f, mProgress * 360 / mMaxProgress)) {
                canvas?.drawCircle((cos(Math.toRadians(lastAngle + angle.toDouble())) * (h / 2 - h*mWithPart/2)).toFloat() + w / 2,
                    (sin(Math.toRadians(lastAngle + angle.toDouble())) * (h / 2 - h*mWithPart/2)).toFloat() + h / 2,
                    h*mWithPart/2, mPaint)
                lastAngle += angle
            }
        }

        if (mTypeFaceId != null) {
            val customTypeface = ResourcesCompat.getFont(context, mTypeFaceId!!)
            mPaint.typeface = customTypeface
        }

        mPaint.color = mColorText
        mPaint.isFakeBoldText = true
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.textSize = h * mFontPart
        canvas?.drawText(String.format(Locale.US, mCenterTextFormat, mProgress), w / 2.toFloat(), h / 2.toFloat() + h*mFontPart/2.2f, mPaint)
    }
}