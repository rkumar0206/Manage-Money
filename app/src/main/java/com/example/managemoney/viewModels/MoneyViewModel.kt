package com.example.managemoney.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.managemoney.database.entities.MoneyEntity
import com.example.managemoney.repositories.MoneyRepository
import kotlinx.coroutines.launch

class MoneyViewModel(application: Application, var repository: MoneyRepository) :
    AndroidViewModel(application) {

    fun insert(money: MoneyEntity) = viewModelScope.launch {

        repository.insert(money)
    }

    fun delete(money: MoneyEntity) = viewModelScope.launch {
        repository.delete(money)
    }

    fun getAllMoney() = repository.getAllMoney()

    fun getAllMoneyByPlace(place: String) = repository.getAllMoneyByPlace(place)
}