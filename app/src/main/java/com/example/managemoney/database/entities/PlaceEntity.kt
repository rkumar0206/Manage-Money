package com.example.managemoney.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "placeOfMoney_table")
data class PlaceEntity(
    var timeStamp: Long?,
    var place: String?
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}