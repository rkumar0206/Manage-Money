package com.example.managemoney.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.managemoney.repositories.PlaceRepository

class PlaceViewModelFactory(
    private val application: Application,
    private val repository: PlaceRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return PlaceViewModel(application, repository = repository) as T
    }
}