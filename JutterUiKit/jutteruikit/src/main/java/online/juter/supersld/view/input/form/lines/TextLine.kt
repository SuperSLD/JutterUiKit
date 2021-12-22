package online.juter.supersld.view.input.form.lines

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import online.juter.supersld.R
import online.juter.supersld.view.input.form.formadapter.bindLayout
import online.juter.supersld.view.input.form.holders.TextLineHolder

/**
 *  Текстовое поле формы.
 *  Просто отображение текста и ничего больше.
 */
@Parcelize
open class TextLine(
    val text: String
) : JTFormLine, Parcelable {

    override fun getAdapterData() =
        R.layout.item_line_text bindLayout TextLineHolder::class.java

    override fun valid() = true
    override fun getLineId(): String?  = null
    override fun getValueType(): Class<*>? = null
    override fun getLineValue(): Any? = null
}