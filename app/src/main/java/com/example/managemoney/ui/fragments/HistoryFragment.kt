package com.example.managemoney.ui.fragments

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.managemoney.R
import com.example.managemoney.adapters.HistoryRV_Adapter
import com.example.managemoney.database.entities.MoneyEntity
import com.example.managemoney.model.messagePlace
import com.example.managemoney.ui.MainActivity
import com.example.managemoney.viewModels.MoneyViewModel
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.history_fragment.*

class HistoryFragment : Fragment(R.layout.history_fragment), View.OnClickListener {

    private lateinit var viewModel: MoneyViewModel

    private var place: String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).moneyViewModel

        getMessage()

        getAllData()
        closeHistoryfragment.setOnClickListener(this)
        addPaymentFAB.setOnClickListener(this)
    }

    private fun getMessage() {

        if (!arguments?.isEmpty!!) {

            val arg = arguments?.let {

                HistoryFragmentArgs.fromBundle(it)
            }

            place = arg?.messageFromHome?.place

            if (place != null) {
                history_titleTV.text = place
            }
        }

    }

    private fun getAllData() {

        viewModel.getAllMoneyByPlace(place!!).observe(viewLifecycleOwner, Observer {

            if (it.isNotEmpty()) {

                setUpRecyclerView(it)
            }
        })
    }

    private fun setUpRecyclerView(list: List<MoneyEntity>?) {

        val adapter = HistoryRV_Adapter()


        list?.let {

            adapter.differ.submitList(it)

            historyRV.apply {

                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                historyRV.adapter = adapter
            }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                deleteItem(adapter.differ.currentList[viewHolder.adapterPosition])

            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(Color.RED)
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }).attachToRecyclerView(historyRV)
    }

    private fun deleteItem(money: MoneyEntity?) {

        money?.let {
            viewModel.delete(it)

            Snackbar.make(coordinatorLayout, "Item Deleted", Snackbar.LENGTH_LONG)
                .setAction("undo") {

                    viewModel.insert(money)
                }.show()
        }
    }


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.closeHistoryfragment -> {

                requireActivity().onBackPressed()
            }

            R.id.addPaymentFAB -> {

                if (place != null) {
                    val action = HistoryFragmentDirections.actionHistoryFragmentToAddRemoveFragment(
                        messagePlace(place)
                    )
                    findNavController().navigate(action)
                }
            }
        }
    }


}