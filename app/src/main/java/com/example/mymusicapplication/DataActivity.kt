package com.example.mymusicapplication

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mymusicapplication.ui.page.EditName
import com.example.mymusicapplication.ui.page.SexChoose
import com.example.mymusicapplication.ui.page.GetBirthDay
import com.example.mymusicapplication.ui.page.GetFeelings
import com.example.mymusicapplication.ui.page.MainPage
import com.example.mymusicapplication.ui.page.PhotoScreen
import com.example.mymusicapplication.ui.page.SelectPreference
import com.example.mymusicapplication.ui.page.ShowAll
import com.example.mymusicapplication.ui.theme.MyMusicApplicationTheme
import kotlinx.coroutines.delay
import java.io.File

class DataActivity : AppCompatActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyMusicApplicationTheme {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    var confirmed by remember { mutableStateOf(false) }
                    var openDialogConfirm by rememberSaveable { mutableStateOf(true) }
                    if (!confirmed) {
                        MainPage()
                    } else {
                        GetProfile()
                    }

                    DialogForProfile(openDialog = openDialogConfirm, confirm = {
                        confirmed = true
                        openDialogConfirm = false
                    }, onDismissRequest = { openDialogConfirm = false })


                }
            }
        }
    }
}

@Preview
@Composable
private fun DialogTest() {
    var b by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        while (true) {
            b = !b
            delay(2000)
        }
    }
    DialogForProfile(openDialog = b, confirm = { /*TODO*/ }) {

    }
}

@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DialogPreview() {
    MyMusicApplicationTheme() {
        DialogForProfile(openDialog = true, confirm = { /*TODO*/ }) {}
    }
}

@Composable
fun DialogForProfile(openDialog: Boolean, confirm: () -> Unit, onDismissRequest: () -> Unit) {
    if (openDialog) {
        AlertDialog(onDismissRequest = onDismissRequest,
            icon = { Icon(Icons.Filled.Edit, contentDescription = null) },
            title = {
                Text(text = "Title")
            },
            text = {
                Text(
                    "This area typically contains the supportive text " + "which presents the details regarding the Dialog's purpose."
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    confirm()
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismissRequest()
                }) {
                    Text("Dismiss")
                }
            })
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetProfile() {
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
            DataPage()
        }
    }

}

@Composable
fun DataPage() {
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
    data object Data : State
    data object Undefined : State

    companion object {
        private val list =
            listOf(Name, Sex, Birthday, Preference, FloatValue, Avatar, Data, Undefined)
    }

    fun nextScreen(): State {
        return list[list.indexOf(this) + 1]
    }
}


