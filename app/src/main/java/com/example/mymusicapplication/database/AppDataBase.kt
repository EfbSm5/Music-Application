package com.example.mymusicapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymusicapplication.JSONData
import com.example.mymusicapplication.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
suspend fun checkDataBase(context: Context): UserProfile {
    return suspendCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            val user = AppDataBase.getDatabase(context).userDao().loadUser()
            continuation.resume(toProfile(user))
        }
    }
}


fun insertDataBase(context: Context, profile: UserProfile) {
    val user = AppDataBase.getDatabase(context).userDao()
    CoroutineScope(Dispatchers.IO).launch {
        user.insertData(profile, context)
    }
}


fun toProfile(JSON: String?): UserProfile {
    if (!JSON.isNullOrEmpty()) {
        val temp = fromJson(JSON)
        return temp
    } else {
        return UserProfile()
    }
}