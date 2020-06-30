package com.example.managemoney.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.managemoney.R
import com.example.managemoney.database.entities.PlaceEntity
import kotlinx.android.synthetic.main.home_rv_layout.view.*
import kotlin.random.Random

class HomeRV_Adapter() : RecyclerView.Adapter<HomeRV_Adapter.PlaceViewHolder>() {

    private var mListener: OnItemClickListener? = null

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {

                mListener?.onItemClick(differ.currentList[pos].place!!, pos)
            }
        }
    }


    val diffCallback = object : DiffUtil.ItemCallback<PlaceEntity>() {
        override fun areItemsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {

            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {

        return PlaceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_rv_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {

        holder.itemView.apply {

            placeTextView.text = differ.currentList[position].place

            val r = Random.nextInt(255)
            val g = Random.nextInt(255)
            val b = Random.nextInt(255)
            cardView.setCardBackgroundColor(Color.rgb(r, g, b))
        }
    }

    interface OnItemClickListener {

        fun onItemClick(place: String, position: Int)

    }

    fun setOnClickListener(listener: OnItemClickListener) {

        mListener = listener
    }

}