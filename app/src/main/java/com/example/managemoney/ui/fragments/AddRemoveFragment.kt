package com.example.managemoney.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.managemoney.R
import com.example.managemoney.database.entities.MoneyEntity
import com.example.managemoney.ui.MainActivity
import com.example.managemoney.utils.WorkingWithDateAndTime
import com.example.managemoney.viewModels.MoneyViewModel
import kotlinx.android.synthetic.main.fragment_add_money.*

class AddRemoveFragment : Fragment(R.layout.fragment_add_money), View.OnClickListener,
    RadioGroup.OnCheckedChangeListener {

    private lateinit var viewModel: MoneyViewModel
    private var currentTime: Long? = null
    private var place: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).moneyViewModel

        getMessage()

        currentTime = System.currentTimeMillis()
        dateTextView.text =
            WorkingWithDateAndTime().convertMillisecondsToDateAndTimePattern(timeInMillis = currentTime)
        timeTextView.text =
            WorkingWithDateAndTime().convertMillisecondsToDateAndTimePattern(
                timeInMillis = currentTime,
                pattern = "hh:mm:ss a"
            )


        if (statusRadioGroup.checkedRadioButtonId == earnedRadioButton.id) {

            whereYouSpentEditText.editText?.hint = getString(R.string.where_you_earned_optional)
        } else {
            whereYouSpentEditText.editText?.hint = getString(R.string.where_you_spent_optional)

        }
        statusRadioGroup.setOnCheckedChangeListener(this)
        setClickListeners()
        addTextWatcher()
    }

    private fun getMessage() {

        if (!arguments?.isEmpty!!) {

            val arg = arguments?.let {

                AddRemoveFragmentArgs.fromBundle(it)
            }

            place = arg?.messageFromHistory?.place

        }
    }


    private fun setClickListeners() {

        fabAdd.setOnClickListener(this)
        fabCancel.setOnClickListener(this)
        closeAddRemoveImageButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.fabAdd -> {

                if (validateForm()) {

                    val checkedStatus =
                        requireActivity().findViewById<RadioButton>(statusRadioGroup.checkedRadioButtonId)
                    addToDatabase(
                        currentTime,
                        amountEditText.editText?.text.toString().trim(),
                        whereYouSpentEditText.editText?.text.toString().trim(),
                        checkedStatus.text.toString().trim()
                    )
                }
            }

            R.id.fabCancel -> {
                requireActivity().onBackPressed()
            }
            R.id.closeAddRemoveImageButton -> {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun addToDatabase(currentTime: Long?, amount: String, onWhat: String, status: String) {

        val money = MoneyEntity(
            currentTime,
            amount.toDouble(),
            status,
            onWhat,
            place
        )

        viewModel.insert(money)

        Toast.makeText(requireContext(), "Added to database", Toast.LENGTH_SHORT).show()
        requireActivity().onBackPressed()
    }


    @SuppressLint("SetTextI18n")
    private fun validateForm(): Boolean {

        if (whereYouSpentEditText.editText?.text.toString().trim().isEmpty()) {
            whereYouSpentEditText.editText?.setText("Not Available!!!")
        }
        if (amountEditText.editText?.text.toString().trim().isEmpty()) {

            amountEditText.error = "Field can't be empty!!!"
        }
        return amountEditText.error == null
    }

    private fun addTextWatcher() {

        amountEditText.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s?.trim()?.isEmpty()!!) {
                    amountEditText.error = "Field can't be empty!!!"
                } else {
                    amountEditText.error = null
                }
            }
        })

    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

        if (checkedId == earnedRadioButton.id) {

            whereYouSpentEditText.editText?.hint = getString(R.string.where_you_earned_optional)
        } else {

            whereYouSpentEditText.editText?.hint = getString(R.string.where_you_spent_optional)
        }

    }

}