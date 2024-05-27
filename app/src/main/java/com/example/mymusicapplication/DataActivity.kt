package com.example.mymusicapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mymusicapplication.ui.page.EditName
import com.example.mymusicapplication.ui.page.SexChoose
import com.example.mymusicapplication.ui.page.GetBirthDay
import com.example.mymusicapplication.ui.page.GetFeelings
import com.example.mymusicapplication.ui.page.PhotoScreen
import com.example.mymusicapplication.ui.page.SelectPreference
import com.example.mymusicapplication.ui.theme.MyMusicApplicationTheme
import java.io.File

class DataActivity : AppCompatActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyMusicApplicationTheme {
//                val progress = remember {
//                    mutableStateOf<Float>(0.0F)
//               }
//              LinearProgressIndicator(progress = progress.value)
                Scaffold(topBar = {
                    TopAppBar(title = { Text(text = "让我们更了解你") })
                }) {
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
}

@Composable
fun DataApp() {
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
                onPerferenceConfirmed = { profile = profile.copy(preference = it) },
                onNavigateToNextScreen = onNavigateToNextScreen
            )
        }

        else -> {

        }
    }

}

data class UserProfile(
    val name: String = "",
    val sex: String = "",
    val birthDay: String = "",
    val preference: List<String>? = null,
    val useEmotion: Float = 0f,
    val photoFile: File? = null
)

sealed interface State {
    data object Name : State
    data object Sex : State
    data object Birthday : State
    data object Preference : State
    data object FloatValue : State
    data object Avatar : State
    data object Undefined : State

    companion object {
        private val list = listOf(Name, Sex, Birthday, Preference, FloatValue, Avatar, Undefined)
    }

    fun nextScreen(): State {
        return list[list.indexOf(this) + 1]
    }
}


