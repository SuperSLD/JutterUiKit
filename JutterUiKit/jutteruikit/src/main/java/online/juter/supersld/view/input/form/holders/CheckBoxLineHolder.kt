package online.juter.supersld.view.input.form.holders

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.item_line_check_box.view.*
import online.juter.supersld.view.input.form.formadapter.JTFormBaseHolder
import online.juter.supersld.view.input.form.lines.CheckBoxLine
import online.juter.supersld.view.input.form.lines.JTFormLine

class CheckBoxLineHolder(itemView: View) : JTFormBaseHolder(itemView) {
    @SuppressLint("SetTextI18n")
    override fun bind(line: JTFormLine) {
        line as CheckBoxLine
        with(itemView) {
            cbFlag.text = line.text + if(line.mandatory) "*" else ""
            cbFlag.isChecked = line.checked
            cbFlag.setTextColor(getParams().colorTextPrimary)
            cbFlag.setOnCheckedChangeListener { _, isChecked ->
                line.checked = isChecked
            }
        }
    }
}