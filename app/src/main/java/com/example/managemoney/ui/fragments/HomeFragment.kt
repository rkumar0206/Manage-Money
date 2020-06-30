package com.example.managemoney.ui.fragments

import android.os.Bundle
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
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(R.layout.home_fragment),
    View.OnClickListener, HomeRV_Adapter.OnItemClickListener {

    private lateinit var placeViewModel: PlaceViewModel

    /* private lateinit var entryAdd: ArrayList<Entry>*/
    private val TAG = "HomeFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placeViewModel = (activity as MainActivity).placeViewModel
        /*  entryAdd = ArrayList()*/

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

        val adapter = HomeRV_Adapter()
        list?.let {

            adapter.differ.submitList(it)
            homeRV.apply {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                homeRV.adapter = adapter

            }
        }

        adapter.setOnClickListener(this)

    }

    override fun onItemClick(place: String, position: Int) {

        val action = HomeFragmentDirections.actionHomeFragmentToHistoryFragment(messagePlace(place))
        findNavController().navigate(action)

    }


    /*  private fun addToChart(x: Float, y: Float) {
          entryAdd.add(Entry(x, y))

          val set1: LineDataSet
          if (lineChart.data != null &&
              lineChart.data.dataSetCount > 0
          ) {
              set1 = lineChart.data.getDataSetByIndex(0) as LineDataSet
              set1.values = entryAdd
              lineChart.data.notifyDataChanged()
              lineChart.notifyDataSetChanged()
          } else {
              set1 = LineDataSet(entryAdd, "Data")
              //set1.setDrawIcons(true)

              set1.setCircleColor(Color.GREEN)
              //set1.setCircleColorHole(Color.BLUE)
              //set1.circleHoleRadius = 3f
              set1.enableDashedLine(10f, 5f, 0f)
              set1.enableDashedHighlightLine(10f, 5f, 0f)
              set1.color = Color.DKGRAY
              //set1.setCircleColor(Color.DKGRAY)
              set1.lineWidth = 1f
              //set1.circleRadius = 3f
              set1.valueTextSize = 9f
              //set1.setDrawFilled(true)
              set1.formLineWidth = 1f
              set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
              set1.formSize = 15f
              //set1.fillColor = Color.DKGRAY

              val dataSets: ArrayList<ILineDataSet> = ArrayList()
              dataSets.add(set1)
              val data = LineData(dataSets)
              lineChart.data = data

          }
      }*/

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.fabAddPlace -> {
                findNavController().navigate(R.id.action_homeFragment_to_addPlaceFragment)
            }
        }
    }


}