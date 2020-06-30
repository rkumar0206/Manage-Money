package com.example.managemoney.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.managemoney.R
import com.example.managemoney.database.databases.MoneyDatabase
import com.example.managemoney.database.databases.PlaceDatabase
import com.example.managemoney.repositories.MoneyRepository
import com.example.managemoney.repositories.PlaceRepository
import com.example.managemoney.viewModels.MoneyViewModel
import com.example.managemoney.viewModels.MoneyViewModelFactory
import com.example.managemoney.viewModels.PlaceViewModel
import com.example.managemoney.viewModels.PlaceViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var moneyViewModel: MoneyViewModel
    lateinit var placeViewModel: PlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moneyRepository = MoneyRepository(
            MoneyDatabase(this)
        )

        val placeRepository = PlaceRepository(
            PlaceDatabase(this)
        )


        val moneyViewModelFactory = MoneyViewModelFactory(application, moneyRepository)

        moneyViewModel = ViewModelProvider(this, moneyViewModelFactory)
            .get(MoneyViewModel::class.java)

        val placeViewModelFactory = PlaceViewModelFactory(application, placeRepository)
        placeViewModel =
            ViewModelProvider(this, placeViewModelFactory).get(PlaceViewModel::class.java)

    }
}