package com.jizhe7550.myapplication.ui.pickcharm.charmsgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jizhe7550.myapplication.R
import com.jizhe7550.myapplication.model.CharmModel
import de.hdodenhof.circleimageview.CircleImageView


class CircleViewAdapter(private val type: CircleViewType = CircleViewType.IMAGE) :
    RecyclerView.Adapter<CircleViewAdapter.VH>() {

    enum class CircleViewType {
        IMAGE, TEXT
    }

    private var items = listOf<CharmModel>()

    fun setItems(list: List<CharmModel>) {
        this.items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.adapter_item_circle_view, parent, false),
            type
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    class VH(view: View, private val type: CircleViewType) : RecyclerView.ViewHolder(view) {

        private val imageView: CircleImageView = itemView.findViewById(R.id.imageView)
        private val textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(charmModel: CharmModel) {
            when (type) {
                CircleViewType.IMAGE -> {
                    imageView.visibility = View.VISIBLE
                    textView.visibility = View.INVISIBLE

                    Glide.with(imageView.context).load(charmModel.url)
                        .placeholder(R.drawable.ic_photos_48dp)
                        .into(imageView)
                }

                CircleViewType.TEXT -> {
                    imageView.visibility = View.VISIBLE
                    textView.visibility = View.VISIBLE

                    textView.text = layoutPosition.toString()
                    Glide.with(imageView.context).load(R.drawable.first_row_color).into(imageView)
                }

            }
        }
    }
}
