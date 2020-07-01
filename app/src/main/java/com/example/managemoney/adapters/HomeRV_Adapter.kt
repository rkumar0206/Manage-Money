package com.example.managemoney.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {

                mListener?.onItemClick(getItem(pos).place!!, pos)
            }
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

    /*  val diffCallback = object : DiffUtil.ItemCallback<PlaceEntity>() {
          override fun areItemsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {

              return oldItem.id == newItem.id
          }

          override fun areContentsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {

              return oldItem.place == newItem.place &&
                      oldItem.timeStamp == newItem.timeStamp
          }
      }

      val differ = AsyncListDiffer(this, diffCallback)*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {

        return PlaceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_rv_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {

        holder.itemView.apply {

            placeTextView.text = getItem(position).place

            val r = Random.nextInt(255)
            val g = Random.nextInt(255)
            val b = Random.nextInt(255)
            cardView.setCardBackgroundColor(Color.rgb(r, g, b))
        }
    }

    fun getPlace(position: Int): PlaceEntity {

        return getItem(position)
    }

    interface OnItemClickListener {

        fun onItemClick(place: String, position: Int)

    }

    fun setOnClickListener(listener: OnItemClickListener) {

        mListener = listener
    }

}