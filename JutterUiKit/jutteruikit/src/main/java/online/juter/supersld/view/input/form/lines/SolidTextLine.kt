package online.juter.supersld.view.input.form.lines

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import online.juter.supersld.R
import online.juter.supersld.view.input.form.formadapter.bindLayout
import online.juter.supersld.view.input.form.holders.SolidLineHolder

/**
 *  Текстовое поле формы с рамкой.
 *  Просто отображение текста и ничего больше.
 */
@Parcelize
class SolidTextLine(
    val text: String
) : JTFormLine, Parcelable {

    override fun getAdapterData() =
        R.layout.item_line_text_solid bindLayout SolidLineHolder::class.java

    override fun valid() = true
    override fun getLineId(): String? = null
    override fun getValueType(): Class<*>? = null
    override fun getLineValue(): Any? = null
}