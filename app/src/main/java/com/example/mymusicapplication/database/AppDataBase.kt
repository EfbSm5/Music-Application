package com.example.mymusicapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymusicapplication.JSONData

@Database(version = 1, entities = [JSONData::class], exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): ProfileDuo

    companion object {
        private var instance: AppDataBase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDataBase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "app_database"
            ).build().apply { instance = this }
        }

    }
}