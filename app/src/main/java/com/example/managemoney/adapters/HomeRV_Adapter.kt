package com.example.managemoney.adapters

import android.graphics.Color
import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.managemoney.R
import com.example.managemoney.database.entities.PlaceEntity
import kotlinx.android.synthetic.main.home_rv_layout.view.*
import kotlin.random.Random

class HomeRV_Adapter() : ListAdapter<PlaceEntity, HomeRV_Adapter.PlaceViewHolder>(DiffCallback()) {

    private var mListener: OnItemClickListener? = null

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        init {
            itemView.setOnClickListener(this)
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onClick(v: View?) {

            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {

                mListener?.onItemClick(getItem(pos).place!!, pos)
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val delete = menu?.add(1, 1, 1, "Delete")
            delete?.setOnMenuItemClickListener(this)
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {

            when (item?.itemId) {

                1 -> {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION && mListener != null) {

                        mListener?.onDeleteClick(getItem(pos))

                    }
                    return true
                }
            }
            return false
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<PlaceEntity>() {
        override fun areItemsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {

            return oldItem.place == newItem.place &&
                    oldItem.timeStamp == newItem.timeStamp
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {

        return PlaceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_rv_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {

        holder.itemView.apply {

            placeTextView.text = getItem(position).place

            val r = Random.nextInt(60, 255)
            val g = Random.nextInt(60, 255)
            val b = Random.nextInt(60, 255)
            cardView.setCardBackgroundColor(Color.argb(100, r, g, b))

        }
    }

    fun getPlace(position: Int): PlaceEntity {

        return getItem(position)
    }

    interface OnItemClickListener {

        fun onItemClick(place: String, position: Int)
        fun onDeleteClick(placeEntity: PlaceEntity)

    }

    fun setOnClickListener(listener: OnItemClickListener) {

        mListener = listener
    }

}