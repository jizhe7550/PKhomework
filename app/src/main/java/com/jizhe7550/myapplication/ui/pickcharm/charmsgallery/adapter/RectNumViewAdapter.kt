package com.jizhe7550.myapplication.ui.pickcharm.charmsgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jizhe7550.myapplication.model.CharmModel
import com.jizhe7550.myapplication.R


class RectNumViewAdapter : RecyclerView.Adapter<RectNumViewAdapter.VH>() {

    private var items = listOf<CharmModel>()

    fun setItems(list: List<CharmModel>) {
        this.items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.adapter_item_rect_num_view, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {

        private val textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(charm: CharmModel) {
            textView.text = layoutPosition.toString()
        }
    }
}
