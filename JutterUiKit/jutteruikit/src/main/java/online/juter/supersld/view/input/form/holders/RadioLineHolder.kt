package online.juter.supersld.view.input.form.holders

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.item_line_radio.view.*
import online.juter.supersld.view.input.form.formadapter.JTFormBaseHolder
import online.juter.supersld.view.input.form.lines.JTFormLine
import online.juter.supersld.view.input.form.lines.RadioLine

class RadioLineHolder(itemView: View) : JTFormBaseHolder(itemView) {

    @SuppressLint("SetTextI18n")
    override fun bind(line: JTFormLine) {
        line as RadioLine
        with(itemView) {
            tvName.text = line.title + if (line.mandatory) "*" else ""
            tvName.setTextColor(getParams().colorTextPrimary)

            var rprms: RadioGroup.LayoutParams?

            for (i in line.list.indices) {
                val radioButton = RadioButton(context)
                radioButton.text = line.list[i].text
                radioButton.id = line.list[i].id.hashCode()
                radioButton.setTextColor(getParams().colorTextPrimary)
                rprms = RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                rprms.bottomMargin = 16
                vgRadio.addView(radioButton, rprms)
            }

            vgRadio.check(line.selectedItem.hashCode())
            vgRadio.setOnCheckedChangeListener {_, checkedId ->
                line.setSelected(checkedId)
            }
        }
    }
}