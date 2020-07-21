package com.example.managemoney.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.managemoney.R
import com.example.managemoney.database.entities.MoneyEntity
import com.example.managemoney.utils.WorkingWithDateAndTime
import kotlinx.android.synthetic.main.history_rv_layout.view.*

class HistoryRV_Adapter() :
    ListAdapter<MoneyEntity, HistoryRV_Adapter.HistoryViewHolder>(MoneyItemDifferCallBack()) {

    private var mListener: OnItemClickListener? = null

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        init {
            itemView.setOnCreateContextMenuListener(this)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {

        return HistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_rv_layout, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

        holder.itemView.apply {

            /* val r = Random.nextInt(60, 255)
             val g = Random.nextInt(60, 255)
             val b = Random.nextInt(60, 255)
             historyFragCV.setCardBackgroundColor(Color.argb(80, r, g, b))
 */
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

    interface OnItemClickListener {
        fun onDeleteClick(moneyEntity: MoneyEntity)
    }

    fun setOnClickListener(listener: OnItemClickListener) {

        mListener = listener
    }

}