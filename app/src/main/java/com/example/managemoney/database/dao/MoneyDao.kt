package com.example.managemoney.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.managemoney.database.entities.MoneyEntity

@Dao
interface MoneyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(money: MoneyEntity): Long

    @Delete
    suspend fun delete(money: MoneyEntity)

    @Query("SELECT * FROM money_table_DATABASE ORDER BY timeStamp DESC")
    fun getAllMoney(): LiveData<List<MoneyEntity>>

    @Query("SELECT * FROM MONEY_TABLE_DATABASE WHERE place = :place ORDER BY timeStamp DESC")
    fun getAllMoneyByPlace(place: String): LiveData<List<MoneyEntity>>

}