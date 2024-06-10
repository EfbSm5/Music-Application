package com.example.mymusicapplication

import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

data class UserProfile(
    val name: String = "",
    val sex: String = "不详",
    val birthDay: String = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time),
    val preference: List<String> = listOf(""),
    val useEmotion: Float = 0.5f,
    val photoFile: File? = null
)



