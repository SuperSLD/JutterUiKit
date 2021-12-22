package online.juter.supersld.view.input.form.lines

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import online.juter.supersld.R
import online.juter.supersld.view.input.form.formadapter.bindLayout
import online.juter.supersld.view.input.form.holders.CheckBoxLineHolder

/**
 *  Чекбокс
 */
@Parcelize
data class CheckBoxLine(
    val id: String,
    var checked: Boolean = false,
    val text: String,
    val mandatory: Boolean = false
) : JTFormLine, Parcelable {

    override fun getAdapterData() =
        R.layout.item_line_check_box bindLayout CheckBoxLineHolder::class.java

    override fun valid(): Boolean {
        return (mandatory && checked) || !mandatory
    }

    override fun getLineId() = id
    override fun getValueType() = Boolean::class.java
    override fun getLineValue() = checked
}