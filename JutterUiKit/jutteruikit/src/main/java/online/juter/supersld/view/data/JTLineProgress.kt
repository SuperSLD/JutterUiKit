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
import java.lang.Math.abs

/**
 * Горизонтальное отображение прогресса.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
class JTLineProgress : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    private var mProgress = 0f
    private var mMaxProgress = 100f
    private var mColor = Color.parseColor("#EA4335")
    private var mColorBack: Int? = null

    private var mDuration = 4000L
    private var mAnimator: ValueAnimator? = null

    /**
     * Установка продолжителькости анимации.
     * @param duration текст
     */
    fun setDuration(duration: Long) {
        mDuration = duration
    }

    /**
     * Установка цвета заполненной линии.
     * @param color цвет 1
     */
    fun setColor(color: Int) {
        mColor = color
    }

    /**
     * Установка цыета незаполненной части.
     * @param color цвет которым будет заполнена пустая часть.
     */
    fun setEmptyColor(color: Int) {
        mColorBack = color
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
            mAnimator?.end()
            mAnimator = ValueAnimator.ofFloat(mProgress, mProgress + progress)
            with(mAnimator!!) {
                duration = kotlin.math.abs((progress / mMaxProgress) * mDuration).toLong()
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

    /**
     * Обновление значения прогресса
     * @param progress значение прогресса
     *        которое примет прогресс бар с анимацией уменьшения.
     */
    fun updateProgress(progress: Float, animated: Boolean = true) {
        val det = progress - mProgress
        if (animated) {
            mAnimator?.cancel()
            mAnimator = ValueAnimator.ofFloat(mProgress, mProgress + det)
            with(mAnimator!!) {
                duration = kotlin.math.abs((kotlin.math.abs(det) / mMaxProgress) * mDuration).toLong()
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
            mProgress += det
            if (mProgress > mMaxProgress) mProgress = 0f
            invalidate()
        }
    }

    /**
     * Обновление значения прогресса
     * @param progress значение прогресса
     *        которое примет прогресс бар с анимацией уменьшения.
     */
    fun updateProgress(progress: Int, animated: Boolean = true) {
        updateProgress(progress.toFloat(), animated)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val mPaint = Paint()
        val h = measuredHeight
        val w = measuredWidth
        mPaint.flags = Paint.ANTI_ALIAS_FLAG
        mPaint.style = Paint.Style.FILL

        if (mColorBack != null) {
            mPaint.color = mColorBack!!
            canvas?.drawCircle(h/2f, h/2f, h/2f, mPaint)
            canvas?.drawCircle(w - h/2f, h/2f, h/2f, mPaint)
            canvas?.drawRect(h/2f, 0f, w - h/2f, h.toFloat(), mPaint)
        }

        val calcW = (w - h)/mMaxProgress*mProgress + h/2
        mPaint.color = mColor

        canvas?.drawCircle(h/2f, h/2f, h/2f, mPaint)
        canvas?.drawCircle(calcW, h/2f, h/2f, mPaint)
        canvas?.drawRect(h/2f, 0f, calcW, h.toFloat(), mPaint)

    }
}