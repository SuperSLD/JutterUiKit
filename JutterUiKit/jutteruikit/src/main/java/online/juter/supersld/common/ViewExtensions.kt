package online.juter.supersld.common

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat


val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
    get() = (this / Resources.getSystem().displayMetrics.density)

val Float.px: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

fun View.getColor(id: Int) = ContextCompat.getColor(context, id)
fun View.getDrawable(id: Int) = ContextCompat.getDrawable(context, id)
fun View.getString(id: Int) = context.getString(id)
fun View.setBackgroundGradient(color1: String, color2: String) {
    setBackgroundGradient(Color.parseColor(color1), Color.parseColor(color2))
}
fun View.setBackgroundGradient(color1: Int, color2: Int) {
    val gradientDrawable = GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(color1, color2)
    )
    gradientDrawable.cornerRadius = 0f

    this.background = gradientDrawable
}

fun View.setMargins(l: Int, t: Int, r: Int, b: Int) {
    val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    params.setMargins(l, t, r, b)
    this.layoutParams = params
}

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun convertPixelsToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

val Int.toPx get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics)