package com.example.managemoney.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managemoney.R
import com.example.managemoney.adapters.HistoryRV_Adapter
import com.example.managemoney.database.entities.MoneyEntity
import com.example.managemoney.model.messagePlace
import com.example.managemoney.ui.MainActivity
import com.example.managemoney.viewModels.MoneyViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.history_fragment.*

class HistoryFragment : Fragment(R.layout.history_fragment), View.OnClickListener,
    HistoryRV_Adapter.OnItemClickListener {

    private val TAG = "HistoryFragment"
    private lateinit var viewModel: MoneyViewModel

    private var place: String? = null
    private var moneyLeft = 0.0
    private var totalEarned = 0.0
    private var totalSpent = 0.0

    private var mAdapter: HistoryRV_Adapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).moneyViewModel

        getMessage()

        mAdapter = HistoryRV_Adapter()
        getAllData()
        closeHistoryfragment.setOnClickListener(this)
        addPaymentFAB.setOnClickListener(this)
        historyInfoButton.setOnClickListener(this)
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

    @SuppressLint("SetTextI18n")
    private fun getAllData() {

        viewModel.getAllMoneyByPlace(place!!).observe(viewLifecycleOwner, Observer {

            if (it.isNotEmpty()) {

                totalEarned = 0.0
                totalSpent = 0.0

                for (money in it) {

                    Log.d(TAG, "${money.amount}")

                    if (money.status == getString(R.string.earned)) {

                        totalEarned += money.amount!!
                    } else {
                        totalSpent += money.amount!!
                    }
                }

                moneyLeft = totalEarned - totalSpent

                youHaveTextView.text = "You Have ₹ $moneyLeft in your $place"
                totalEarnedTextView.text = "$totalEarned"
                totalSpentTexView.text = "$totalSpent"

                setUpRecyclerView(it)
            }
        })
    }

    private fun setUpRecyclerView(list: List<MoneyEntity>?) {

        list?.let {

            mAdapter?.submitList(it)

            historyRV.apply {

                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = mAdapter
            }
        }

        mAdapter?.setOnClickListener(this)
    }

    private fun deleteItem(money: MoneyEntity?) {

        money?.let {
            viewModel.delete(it)
            Log.d(TAG, "deleted with id ${it.id} amount : ${it.amount}")

            Snackbar.make(coordinatorLayout, "Item Deleted", Snackbar.LENGTH_LONG)
                .setAction("undo") {

                    viewModel.insert(money)
                }.show()
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.historyInfoButton -> {
                showAlertMessage()
            }

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

    private fun showAlertMessage() {

        val message = "\n\nMoney Left :     ₹ $moneyLeft" +
                "\n\nTotal Earned :     ₹ $totalEarned" +
                "\n\nTotal Spent :     ₹ $totalSpent\n"
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->

                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onDeleteClick(moneyEntity: MoneyEntity) {

        deleteItem(moneyEntity)
    }


}