package com.example.managemoney.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.managemoney.database.entities.PlaceEntity
import com.example.managemoney.repositories.PlaceRepository
import kotlinx.coroutines.launch

class PlaceViewModel(app: Application, var repository: PlaceRepository) : AndroidViewModel(app) {

    fun insert(placeEntity: PlaceEntity) = viewModelScope.launch {
        repository.insert(placeEntity)
    }

    fun delete(placeEntity: PlaceEntity) = viewModelScope.launch {
        repository.delete(placeEntity)
    }

    fun getAllPlace() = repository.getAllPlace()
}