package com.example.mymusicapplication

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mymusicapplication.database.Converters
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

@Entity(tableName = "profile")
data class UserProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    @ColumnInfo(name = "name") val name: String = "默认昵称",
    @ColumnInfo(name = "sex") val sex: String = "不详",
    @SuppressLint("SimpleDateFormat") @ColumnInfo(name = "birthDay") val birthDay: String = SimpleDateFormat(
        "yyyy-MM-dd"
    ).format(
        Calendar.getInstance().time
    ),
    @TypeConverters(Converters::class) @ColumnInfo(name = "preference") val preference: List<String> = listOf(
        ""
    ),
    @ColumnInfo(name = "useEmotion") val useEmotion: Float = 0.5f,
    @TypeConverters(Converters::class) @ColumnInfo(name = "photoFile") val photoFile: File? = null
)



