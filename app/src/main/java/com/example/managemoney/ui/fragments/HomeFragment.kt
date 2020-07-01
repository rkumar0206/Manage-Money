package com.example.managemoney.ui.fragments

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.managemoney.R
import com.example.managemoney.adapters.HomeRV_Adapter
import com.example.managemoney.database.entities.PlaceEntity
import com.example.managemoney.model.messagePlace
import com.example.managemoney.ui.MainActivity
import com.example.managemoney.viewModels.PlaceViewModel
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
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

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                deleteItem(mAdapter?.getPlace(viewHolder.adapterPosition))
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
                    .addBackgroundColor(Color.parseColor("#FFFFFF"))
                    .addActionIcon(R.drawable.ic_baseline_delete_black_24)
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
        }).attachToRecyclerView(homeRV)


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