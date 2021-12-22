package online.juter.supersld.view.input.form.holders

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.item_line_text_input.view.*
import online.juter.supersld.view.input.form.formadapter.JTFormBaseHolder
import online.juter.supersld.view.input.form.lines.JTFormLine
import online.juter.supersld.view.input.form.lines.TextInputLine

class TextInputLineHolder(itemView: View) : JTFormBaseHolder(itemView) {
    @SuppressLint("SetTextI18n")
    override fun bind(line: JTFormLine) {
        line as TextInputLine
        with(itemView) {
            etInputLine.setText(line.value)
            tvHint.text = line.hint
            tvHint.setTextColor(getParams().colorTextSecondary)
            with(etInputLine) {
                inputType = line.findInputType()
                minLines = line.minLines
                hint = ""
                setTextColor(getParams().colorTextPrimary)
            }
            with(tvMand) {
                visibility = if (line.mandatory) View.VISIBLE else View.GONE
                text = "*"
                setTextColor(getParams().colorAccent)
            }
            vgContainer.setBackgroundResource(getParams().borderBackground)

            setOnClickListener {
                etInputLine.requestFocus()
            }

            val textWatcher = object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    line.value = etInputLine.text.toString()
                }
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            }

            etInputLine.addTextChangedListener(textWatcher)
        }
    }
}