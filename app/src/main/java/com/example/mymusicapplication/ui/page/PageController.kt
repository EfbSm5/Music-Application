package com.example.mymusicapplication.ui.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.mymusicapplication.QuestionsAndAnswers
import com.example.mymusicapplication.State
import com.example.mymusicapplication.UserProfile

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
            ShowAll(userProfile = profile)
        }

        else -> {

        }
    }

}