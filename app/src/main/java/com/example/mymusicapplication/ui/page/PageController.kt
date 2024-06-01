package com.example.mymusicapplication.ui.page

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mymusicapplication.QuestionsAndAnswers
import com.example.mymusicapplication.State
import com.example.mymusicapplication.UserProfile

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserConfigurationInitializationPage() {
    val sexQuestionsAndAnswers = QuestionsAndAnswers("你的性别", listOf("男", "女", "其他"))
    val preferencesQuestionsAndAnswers = QuestionsAndAnswers(
        "你听歌的偏好", listOf("民谣", "摇滚", "流行", "说唱", "电子", "ACG", "古典", "爵士")
    )

    var profile by remember {
        mutableStateOf(UserProfile())
    }

    var currentScreen: State by remember {
        mutableStateOf(State.Name)
    }
    val onNavigateToNextScreen = {
        currentScreen = currentScreen.nextScreen()
    }

    val progress by remember {
        mutableFloatStateOf(0.0F)
    }


    LinearProgressIndicator(progress = progress)
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "让我们更了解你") })
    }) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            when (currentScreen) {
                State.Name -> {
                    EditName(
                        onNameConfirmed = { profile = profile.copy(name = it) },
                        onNavigateToNextScreen = onNavigateToNextScreen
                    )

                }

                State.Sex -> {
                    SexChoose(
                        sexQuestionsAndAnswers,
                        onSexConfirmed = { profile = profile.copy(sex = it) },
                        onNavigateToNextScreen = onNavigateToNextScreen
                    )
                }

                State.Avatar -> {
                    PhotoScreen(
                        onPhotoConfirmed = { profile = profile.copy(photoFile = it) },
                        onNavigateToNextScreen = onNavigateToNextScreen
                    )
                }

                State.Birthday -> {
                    GetBirthDay(
                        onBirthdayConfirmed = { profile = profile.copy(birthDay = it) },
                        onNavigateToNextScreen = onNavigateToNextScreen
                    )
                }

                State.FloatValue -> {
                    GetFeelings(
                        onEmotionConfirmed = { profile = profile.copy(useEmotion = it) },
                        onNavigateToNextScreen = onNavigateToNextScreen
                    )
                }

                State.Preference -> {
                    SelectPreference(
                        preferencesQuestionsAndAnswers,
                        onPreferenceConfirmed = { profile = profile.copy(preference = it) },
                        onNavigateToNextScreen = onNavigateToNextScreen
                    )
                }

                State.Data -> {
                    ShowAll(userProfile = profile, LocalContext.current)
                }

                else -> {

                }
            }

        }
    }
}