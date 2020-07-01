package com.example.managemoney.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.managemoney.R
import com.example.managemoney.database.entities.MoneyEntity
import com.example.managemoney.utils.WorkingWithDateAndTime
import kotlinx.android.synthetic.main.history_rv_layout.view.*

class HistoryRV_Adapter() :
    ListAdapter<MoneyEntity, HistoryRV_Adapter.HistoryViewHolder>(MoneyItemDifferCallBack()) {


    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class MoneyItemDifferCallBack : DiffUtil.ItemCallback<MoneyEntity>() {
        override fun areItemsTheSame(oldItem: MoneyEntity, newItem: MoneyEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoneyEntity, newItem: MoneyEntity): Boolean {

            return oldItem.status == newItem.status &&
                    oldItem.amount == newItem.amount &&
                    oldItem.onWhat == newItem.onWhat &&
                    oldItem.place == newItem.place &&
                    oldItem.timeStamp == newItem.timeStamp
        }
    }

    fun getItemAtPosition(position: Int): MoneyEntity {

        return getItem(position)
    }

/*
    val differCallback = object : DiffUtil.ItemCallback<MoneyEntity>() {

        override fun areItemsTheSame(oldItem: MoneyEntity, newItem: MoneyEntity): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoneyEntity, newItem: MoneyEntity): Boolean {

            return oldItem.status == newItem.status &&
                    oldItem.amount == newItem.amount &&
                    oldItem.onWhat == newItem.onWhat &&
                    oldItem.place == newItem.place &&
                    oldItem.timeStamp == newItem.timeStamp
        }
    }
*/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {

        return HistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_rv_layout, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

        holder.itemView.apply {

            if (getItem(position).status == context.getString(R.string.earned)) {

                historyAmountTv.setTextColor(Color.parseColor("#669900"))
                historyAmountTv.text = "₹ ${getItem(position).amount.toString()}"
            } else {
                historyAmountTv.setTextColor(Color.parseColor("#ff4444"))
                historyAmountTv.text = "₹ ${getItem(position).amount.toString()}"
            }

            history_dateTextView.text =
                "On ${(WorkingWithDateAndTime()
                    .convertMillisecondsToDateAndTimePattern(
                        getItem(position).timeStamp,
                        "dd-MM-yyyy"
                    ))}"

            history_reasontv.text = "${getItem(position).onWhat}"

        }
    }

}