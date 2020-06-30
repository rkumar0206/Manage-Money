package com.example.managemoney.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.managemoney.database.entities.PlaceEntity

@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(placeEntity: PlaceEntity): Long

    @Delete()
    suspend fun delete(place: PlaceEntity)

    @Query("SELECT * FROM placeOfMoney_table ORDER BY timeStamp DESC")
    fun getAllPlace(): LiveData<List<PlaceEntity>>
}