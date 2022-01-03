package online.juter.supersld.view.input.form.formadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import online.juter.supersld.view.input.form.JTFormParams
import online.juter.supersld.view.input.form.lines.JTFormLine


class JTFormPageAdapter(
    private val mParams: JTFormParams
): RecyclerView.Adapter<JTFormBaseHolder>() {

    private val list: MutableList<JTFormLine> = mutableListOf()

    private val typeData = hashMapOf<Int, Class<*>>()
    override fun getItemViewType(position: Int): Int {
        val data = list[position].getAdapterData()
        if (typeData[data.first] == null) typeData[data.first] = data.second
        return data.first
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JTFormBaseHolder {
        return typeData[viewType]!!.constructors[0].newInstance(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        ) as JTFormBaseHolder
    }

    override fun onBindViewHolder(holder: JTFormBaseHolder, position: Int) {
        holder.setParams(mParams)
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun addAll(lines: MutableList<JTFormLine>) {
        list.clear()
        list.addAll(lines)
        notifyDataSetChanged()
    }
}
