package com.example.managemoney.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managemoney.R
import com.example.managemoney.adapters.HomeRV_Adapter
import com.example.managemoney.database.entities.PlaceEntity
import com.example.managemoney.model.messagePlace
import com.example.managemoney.ui.MainActivity
import com.example.managemoney.viewModels.PlaceViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(R.layout.home_fragment),
    View.OnClickListener, HomeRV_Adapter.OnItemClickListener {

    private lateinit var placeViewModel: PlaceViewModel
    private var mAdapter: HomeRV_Adapter? = null

    /* private lateinit var entryAdd: ArrayList<Entry>*/
    private val TAG = "HomeFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placeViewModel = (activity as MainActivity).placeViewModel
        /*  entryAdd = ArrayList()*/

        mAdapter = HomeRV_Adapter()
        getAllData()
        fabAddPlace.setOnClickListener(this)
        /* seeHistoryButton.setOnClickListener(this)*/
    }

    private fun getAllData() {

        placeViewModel.getAllPlace().observe(viewLifecycleOwner, Observer {

            if (it.isNotEmpty()) {

                noPlaceAddedTextView.visibility = View.GONE
                setUpRecyclerView(it)

            } else {
                noPlaceAddedTextView.visibility = View.VISIBLE

            }
        })

    }

    private fun setUpRecyclerView(list: List<PlaceEntity>?) {

        list?.let {

            mAdapter?.submitList(it)
            homeRV.apply {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = mAdapter

            }
        }

        mAdapter?.setOnClickListener(this)
    }

    private fun deleteItem(item: PlaceEntity?) {

        item?.let {
            placeViewModel.delete(it)
            Log.d(TAG, "deleted place with id ${it.id} place : ${it.place}")

            Snackbar.make(homeCoordinatorLayout, "Item Deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo") {

                    placeViewModel.insert(item)
                }.show()
        }
    }

    override fun onItemClick(place: String, position: Int) {

        val action = HomeFragmentDirections.actionHomeFragmentToHistoryFragment(messagePlace(place))
        findNavController().navigate(action)

    }

    override fun onDeleteClick(placeEntity: PlaceEntity) {

        deleteItem(placeEntity)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.fabAddPlace -> {
                findNavController().navigate(R.id.action_homeFragment_to_addPlaceFragment)
            }
        }
    }


}