package online.juter.supersld.view.input.form.lines

import android.os.Parcelable
import android.text.InputType
import kotlinx.android.parcel.Parcelize
import online.juter.supersld.R
import online.juter.supersld.view.input.form.formadapter.bindLayout
import online.juter.supersld.view.input.form.holders.TextInputLineHolder
import java.lang.IllegalArgumentException

/**
 *  Ввод текста.
 */
@Suppress("IMPLICIT_CAST_TO_ANY")
@Parcelize
data class TextInputLine(
    val id: String,
    val hint: String,
    var value: String = "",
    val inputType: Int = TEXT,
    val mandatory: Boolean = false,
    val minLines: Int = 1
) : JTFormLine, Parcelable {

    companion object {
        const val TEXT = 1
        const val TEXT_MULTILINE = 2
        const val INTEGER = 3

        private val inputTypeMap = hashMapOf(
            TEXT to InputType.TYPE_CLASS_TEXT,
            TEXT_MULTILINE to (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE),
            INTEGER to (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
        )
    }

    fun findInputType() = inputTypeMap[inputType] ?:
        throw IllegalArgumentException("invalid TextInputLine inputType, $inputType not found")

    override fun getAdapterData() =
        R.layout.item_line_text_input bindLayout TextInputLineHolder::class.java

    override fun valid(): Boolean {
        return !mandatory || (mandatory && value.isNotEmpty())
    }

    override fun getLineId() = id
    override fun getValueType() = when(inputType) {
        INTEGER -> Integer::class.java
        else -> String::class.java
    }
    override fun getLineValue() =
        when(inputType) {
            INTEGER -> value.toInt()
            else -> value
        }
}