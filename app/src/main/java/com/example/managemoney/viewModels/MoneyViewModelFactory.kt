package com.example.managemoney.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.managemoney.repositories.MoneyRepository

class MoneyViewModelFactory(
    private val application: Application,
    private val repository: MoneyRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return MoneyViewModel(application, repository = repository) as T
    }
}