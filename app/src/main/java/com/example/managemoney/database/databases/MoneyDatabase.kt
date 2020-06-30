package com.example.managemoney.database.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.managemoney.database.dao.MoneyDao
import com.example.managemoney.database.entities.MoneyEntity

@Database(entities = [MoneyEntity::class], version = 2)
abstract class MoneyDatabase : RoomDatabase() {

    abstract fun getMoneyDao(): MoneyDao

    companion object {

        @Volatile
        private var instance: MoneyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {

            instance
                ?: createDatabase(
                    context
                ).also {

                instance = it
            }
        }

        private fun createDatabase(context: Context): MoneyDatabase {

            return Room.databaseBuilder(
                context,
                MoneyDatabase::class.java,
                "money_database.db"
            ).build()

        }
    }

}