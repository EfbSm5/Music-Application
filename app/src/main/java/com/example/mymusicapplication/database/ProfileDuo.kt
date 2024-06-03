package com.example.mymusicapplication.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymusicapplication.JSONData
import com.example.mymusicapplication.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Dao
interface ProfileDuo {

    suspend fun insertData(userProfile: UserProfile, context: Context) {
        withContext(Dispatchers.IO) {
            val jsonData = toJson(userProfile)
            val userDao = AppDataBase.getDatabase(context).userDao()
            val count = userDao.getCount()
            userDao.insert(JSONData(count + 1, jsonData))
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: JSONData)

    @Query("SELECT COUNT(*) FROM profile")
    fun getCount(): Int

    @Query("select data from profile order by id desc limit 1;")
    fun loadUser(): String?
}
