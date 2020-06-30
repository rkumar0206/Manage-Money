package com.example.managemoney.repositories

import com.example.managemoney.database.databases.PlaceDatabase
import com.example.managemoney.database.entities.PlaceEntity

class PlaceRepository(var db: PlaceDatabase) {

    suspend fun insert(place: PlaceEntity) = db.getDao().insert(place)

    suspend fun delete(place: PlaceEntity) = db.getDao().delete(place)

    fun getAllPlace() = db.getDao().getAllPlace()
}