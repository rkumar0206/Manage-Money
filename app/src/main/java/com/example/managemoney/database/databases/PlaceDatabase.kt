package com.example.managemoney.database.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.managemoney.database.dao.PlaceDao
import com.example.managemoney.database.entities.PlaceEntity

@Database(entities = [PlaceEntity::class], version = 2)
abstract class PlaceDatabase : RoomDatabase() {

    abstract fun getDao(): PlaceDao

    companion object {

        @Volatile
        private var instance: PlaceDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {

            instance ?: createDatabase(context).also {
                instance = it
            }

        }

        private fun createDatabase(context: Context): PlaceDatabase =
            Room.databaseBuilder(
                context,
                PlaceDatabase::class.java,
                "placeOfMoney.db"
            ).build()

    }

}