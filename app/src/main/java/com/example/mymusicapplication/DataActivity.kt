package com.example.mymusicapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.example.mymusicapplication.ui.page.EditName
import com.example.mymusicapplication.ui.page.SingleChoose
import com.example.mymusicapplication.ui.page.GetBirthDay

class DataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DataApp()
        }
    }
}

@Composable
fun DataApp() {
    val sexQuestions =
        Questions("你的性别", listOf("男", "女", "其他"))

    val name = remember { mutableStateListOf<String>() }
    val sex = remember { mutableStateListOf<String>() }
    val birthDay = remember {
        mutableStateListOf<String>()
    }
    if (name.isEmpty()) {
        EditName(name)
    }
    if (name.isNotEmpty() && sex.isEmpty()) {
        SingleChoose(sexQuestions, sex)
    }
    if (sex.isNotEmpty() && birthDay.isEmpty()){
        GetBirthDay(birthDay)
    }
}