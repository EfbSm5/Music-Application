package com.example.mymusicapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymusicapplication.UserProfile

@Dao
interface ProfileDuo {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userProfile: UserProfile)

    @Query("SELECT COUNT(*) FROM profile")
    fun getCount(): Int

    @Query("select * from profile order by id desc limit 1;")
    fun loadUser(): UserProfile

    @Query("select * from profile order by id desc ")
    fun loadAllUsers(): List<UserProfile>
    
}
