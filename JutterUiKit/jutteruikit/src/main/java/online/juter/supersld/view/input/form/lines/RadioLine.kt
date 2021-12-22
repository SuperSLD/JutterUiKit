package online.juter.supersld.view.input.form.lines

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import online.juter.supersld.R
import online.juter.supersld.view.input.form.formadapter.bindLayout
import online.juter.supersld.view.input.form.holders.RadioLineHolder

/**
 *  Выбор из нескольких элементов.
 */
@Parcelize
data class RadioLine(
    val id: String,
    val title: String,
    var selectedItem: String? = null,
    val list: MutableList<RadioItem>,
    val mandatory: Boolean = false
) : JTFormLine, Parcelable {

    override fun getAdapterData() =
        R.layout.item_line_radio bindLayout RadioLineHolder::class.java

    override fun valid(): Boolean {
        return (mandatory && (selectedItem != null)) || !mandatory
    }

    fun setSelected(hash: Int) {
        list.forEach {
            if (it.id.hashCode() == hash) {
                selectedItem = it.id
                return@forEach
            }
        }
    }

    override fun getLineId() = id
    override fun getValueType() = String::class.java
    override fun getLineValue() = selectedItem
}

@Parcelize
data class RadioItem(
    val id: String,
    val text: String
) : Parcelable