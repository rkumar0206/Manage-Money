package com.example.managemoney.repositories

import com.example.managemoney.database.databases.MoneyDatabase
import com.example.managemoney.database.entities.MoneyEntity

class MoneyRepository(var database: MoneyDatabase) {

    suspend fun insert(money: MoneyEntity) = database.getMoneyDao().insert(money)

    suspend fun delete(money: MoneyEntity) = database.getMoneyDao().delete(money)

    fun getAllMoney() = database.getMoneyDao().getAllMoney()

    fun getAllMoneyByPlace(place: String) = database.getMoneyDao().getAllMoneyByPlace(place)
}