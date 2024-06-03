package com.example.mymusicapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File

@Entity(tableName = "profile")
data class UserProfile(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,

    @ColumnInfo(name = "name") val name: String = "默认昵称",

    @ColumnInfo(name = "sex") val sex: String = "不详",

    @ColumnInfo(name = "birthday") val birthDay: String = "",

    @ColumnInfo(name = "preference") val preference: List<String> = listOf(""),

    @ColumnInfo(name = "useEmotion") val useEmotion: Float = 0f,

    @ColumnInfo(name = "photoFile") val photoFile: File? = null
)

