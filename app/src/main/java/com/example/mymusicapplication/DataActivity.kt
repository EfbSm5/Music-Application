package com.example.mymusicapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mymusicapplication.ui.page.EditName
import com.example.mymusicapplication.ui.page.SexChoose
import com.example.mymusicapplication.ui.page.GetBirthDay
import com.example.mymusicapplication.ui.page.GetFeelings
import com.example.mymusicapplication.ui.page.SelectPreference
import com.example.mymusicapplication.ui.theme.MyMusicApplicationTheme

class DataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyMusicApplicationTheme {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    DataApp()
                }
            }
        }
    }
}

@Composable
fun DataApp() {
    val sexQuestionsAndAnswers = QuestionsAndAnswers("你的性别", listOf("男", "女", "其他"))
    val preferencesQuestionsAndAnswers = QuestionsAndAnswers(
        "你听歌的偏好", listOf("民谣", "摇滚", "流行", "说唱", "电子", "ACG", "古典", "爵士")
    )

    val name = remember { mutableStateListOf<String>() }
    val sex = remember { mutableStateListOf<String>() }
    val birthDay = remember { mutableStateListOf<String>() }
    val preference = remember { mutableStateListOf<String>() }
    val useEmotion = remember { mutableStateListOf<Float>() }
    if (name.isEmpty()) {
        EditName(name)
    }
    if (name.isNotEmpty() && sex.isEmpty()) {
        SexChoose(sexQuestionsAndAnswers, sex)
    }
    if (sex.isNotEmpty() && birthDay.isEmpty()) {
        GetBirthDay(birthDay)
    }
    if (birthDay.isNotEmpty() && preference.isEmpty()) {
        SelectPreference(preferencesQuestionsAndAnswers, preference)
    }
    if (preference.isNotEmpty() && useEmotion.isEmpty()) {
        GetFeelings(useEmotion)
    }
}