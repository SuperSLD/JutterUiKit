package online.juter.supersld.view.input.form

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import online.juter.supersld.view.input.form.lines.JTFormLine

@Parcelize
data class JTFormPage(
    val lines: MutableList<JTFormLine>,
    val buttonText: String,
    val endAction: String? = null
) : Parcelable {
    companion object {
        const val LAST_PAGE = -1
    }

    fun isValid(): Boolean {
        var isValid = true
        lines.forEach {
            if (!it.valid()) isValid = false
        }
        return isValid
    }
}