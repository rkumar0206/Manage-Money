package com.example.managemoney.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "money_table_DATABASE")
data class MoneyEntity(
    var timeStamp: Long? = null,
    var amount: Double?,
    var status: String?,
    var onWhat: String?,
    var place: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}