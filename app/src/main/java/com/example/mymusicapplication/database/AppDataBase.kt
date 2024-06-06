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
                context.applicationContext, AppDataBase::class.java, "app_database"
            ).build().apply { instance = this }
        }

    }
}

fun checkDataBase(context: Context, getUserProfile: (List<UserProfile>?) -> Unit) {
    val user = AppDataBase.getDatabase(context).userDao().loadAllUsers()
    val userList = mutableListOf<UserProfile>()
    for (i in user) {
        val profile = toProfile(i)
        if (profile != null) {
            userList.add(profile)
        }
    }
    getUserProfile(userList)
}


fun insertDataBase(context: Context, profile: UserProfile) {
    val user = AppDataBase.getDatabase(context).userDao()
    CoroutineScope(Dispatchers.IO).launch {
        user.insertData(profile, context)
    }
}


fun toProfile(jsonData: String?): UserProfile? {
    return if (jsonData.isNullOrEmpty()) {
        null
    } else {
        fromJson(jsonData)
    }
}