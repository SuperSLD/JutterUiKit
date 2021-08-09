package online.juter.supersld.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class JTAbstractViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Any?)
}