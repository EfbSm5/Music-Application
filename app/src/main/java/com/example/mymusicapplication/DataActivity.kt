package com.example.mymusicapplication

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.example.mymusicapplication.ui.page.EditName
import com.example.mymusicapplication.ui.page.SingleChoose

class DataActivity : AppCompatActivity() {
    private val sexQuestions =
        Questions("你的性别", listOf("男", "女", "其他"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var name = remember { mutableStateListOf<String>() }
            val sex = remember { mutableStateListOf<String>() }
            EditName(name, onClick = {
                if (it.isNotEmpty()) {
                    name = name.apply {
                        add(it)
                    }
                }
                Log.e(ContentValues.TAG, "EditName: ${name[0]}")

            })
            if (name.isNotEmpty()) {
                SingleChoose(sexQuestions, sex)
            }

        }
    }
}