package online.juter.supersld.view.input.form.holders

import android.view.View
import kotlinx.android.synthetic.main.item_line_text.view.*
import online.juter.supersld.view.input.form.formadapter.JTFormBaseHolder
import online.juter.supersld.view.input.form.lines.JTFormLine
import online.juter.supersld.view.input.form.lines.SolidTextLine
import online.juter.supersld.view.input.form.lines.TextLine

class SolidLineHolder(itemView: View) : JTFormBaseHolder(itemView) {
    override fun bind(line: JTFormLine) {
        line as SolidTextLine
        with(itemView) {
            tvText.text = line.text
            tvText.setTextColor(getParams().colorTextPrimary)
            tvText.setBackgroundColor(getParams().colorBorder)
        }
    }
}