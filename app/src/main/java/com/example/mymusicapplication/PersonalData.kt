package com.example.mymusicapplication

import java.io.File


data class UserProfile(
    val name: String = "默认昵称",
    val sex: String = "不详",
    val birthDay: String = "",
    val preference: List<String> = listOf(""),
    val useEmotion: Float = 0f,
    val photoFile: File? = null
)

