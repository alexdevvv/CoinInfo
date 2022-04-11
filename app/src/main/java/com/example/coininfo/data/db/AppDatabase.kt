package com.example.coininfo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coininfo.model.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private const val NAME_DATABASE = "database"
        private var db: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            db?.let { return it }
            val instance: AppDatabase =
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    NAME_DATABASE
                ).build()
            db = instance
            return instance
        }
    }

    abstract fun coinPriceInfoDao(): CoinPriceInfoDao
}