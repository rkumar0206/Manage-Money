package com.example.managemoney.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.managemoney.R
import com.example.managemoney.database.entities.PlaceEntity
import com.example.managemoney.ui.MainActivity
import com.example.managemoney.viewModels.PlaceViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_add_place.*
import java.util.*

class AddPlaceFragment : Fragment(R.layout.fragment_add_place), View.OnClickListener {

    private lateinit var placeViewModel: PlaceViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placeViewModel = (activity as MainActivity).placeViewModel

        closeAddPlaceFragment.setOnClickListener(this)
        cancelFAB.setOnClickListener(this)
        addPlaceFAB.setOnClickListener(this)
        addPlaceInfoButton.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.addPlaceFAB -> {

                checkAndAddToDatabase()
            }

            R.id.cancelFAB -> {
                requireActivity().onBackPressed()
            }

            R.id.closeAddPlaceFragment -> {

                requireActivity().onBackPressed()
            }

            R.id.addPlaceInfoButton -> {
                showMessage()
            }

        }

    }

    private fun showMessage() {

        MaterialAlertDialogBuilder(requireContext())
            .setMessage(
                "Enter the place of the money.\n\n" +
                        "For Example : Bank, Locker, Purse, Home, etc."
            )
            .setPositiveButton("Ok") { dialog, _ ->

                dialog.dismiss()
            }
            .create()
            .show()

    }

    private fun checkAndAddToDatabase() {

        if (addPlaceEditText?.editText?.text.toString().trim().isNotEmpty()) {

            placeViewModel.getAllPlace().observe(viewLifecycleOwner, Observer {

                if (it.isNotEmpty()) {

                    var flag = true
                    for (i in it) {

                        if (i.place?.toLowerCase(Locale.ROOT) == addPlaceEditText?.editText?.text.toString()
                                .trim().toLowerCase(
                                    Locale.ROOT
                                )
                        ) {
                            Toast.makeText(
                                requireContext(),
                                "This place is already there!!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            flag = false
                            break
                        }
                    }

                    if (flag) {
                        addToDatabase(
                            addPlaceEditText?.editText?.text.toString().trim()
                        )
                    }
                } else {
                    addToDatabase(
                        addPlaceEditText?.editText?.text.toString().trim()
                    )
                }
            })
        } else {

            addPlaceEditText?.error = "Field can't be empty!!!"
            Toast.makeText(
                requireContext(),
                "Please enter the place!!!",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun addToDatabase(place: String?) {

        place?.let {

            val placeEntity = PlaceEntity(
                System.currentTimeMillis(),
                it
            )

            placeViewModel.insert(placeEntity)

            Toast.makeText(requireContext(), "Place Added", Toast.LENGTH_SHORT).show()

            requireActivity().onBackPressed()
        }
    }


}