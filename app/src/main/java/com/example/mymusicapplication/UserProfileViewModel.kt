package com.example.mymusicapplication

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymusicapplication.ui.page.EditName
import com.example.mymusicapplication.ui.page.EditSex
import com.example.mymusicapplication.ui.page.EditBirthDay
import com.example.mymusicapplication.ui.page.EditPreference
import com.example.mymusicapplication.ui.page.EditEmotion
import com.example.mymusicapplication.ui.page.PhotoScreen
import com.example.mymusicapplication.ui.page.ShowAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File


class EditUserProfileViewModel : ViewModel() {
    private val _profile = MutableStateFlow(UserProfile())
    val profile: StateFlow<UserProfile> = _profile

    fun updateName(name: String) {
        _profile.value = _profile.value.copy(name = name)
    }

    fun updateSex(sex: String) {
        _profile.value = _profile.value.copy(sex = sex)
    }

    fun updateBirthday(birthday: String) {
        _profile.value = _profile.value.copy(birthDay = birthday)
    }

    fun updatePreferences(preferences: List<String>) {
        _profile.value = _profile.value.copy(preference = preferences)
    }

    fun updateEmotion(emotion: Float) {
        _profile.value = _profile.value.copy(useEmotion = emotion)
    }

    fun updateAvatar(avatar: File) {
        _profile.value = _profile.value.copy(photoFile = avatar)
    }
}

val list = listOf(
    "editName", "editSex", "editBirthday", "editPreferences", "editEmotion", "editAvatar", "summary"
)

fun nextScreen(navController: NavController) {
    val currentDestination = navController.currentBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val index = list.indexOf(currentRoute)
    val nextRoute = if (index in 0 until list.lastIndex) {
        list[index + 1]
    } else {
        null
    }
    if (nextRoute != null) {
        navController.navigate(nextRoute)
    }
}

fun lastScreen(navController: NavController) {
    val currentDestination = navController.currentBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val index = list.indexOf(currentRoute)
    val lastRoute = if (index in list.indices) {
        list[index - 1]
    } else {
        null
    }
    if (lastRoute != null) {
        navController.navigate(lastRoute)
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SurveyNavGraph(viewModel: EditUserProfileViewModel) {
    val sexQuestionsAndAnswers = QuestionsAndAnswers("你的性别", listOf("男", "女", "其他"))
    val preferencesQuestionsAndAnswers = QuestionsAndAnswers(
        "你听歌的偏好", listOf("民谣", "摇滚", "流行", "说唱", "电子", "ACG", "古典", "爵士")
    )


    val navController = rememberNavController()
    NavHost(navController, startDestination = "editName") {
        composable("editName") {
            EditName(viewModel, navController)
        }
        composable("editSex") {
            EditSex(sexQuestionsAndAnswers, viewModel, navController)
        }
        composable("editBirthday") {
            EditBirthDay(viewModel, navController)
        }
        composable("editPreferences") {
            EditPreference(preferencesQuestionsAndAnswers, viewModel, navController)
        }
        composable("editEmotion") {
            EditEmotion(viewModel, navController)
        }
        composable("editAvatar") {
            PhotoScreen(viewModel, navController)
        }
        composable("summary") {
            ShowAll(viewModel.profile.value, LocalContext.current)
        }
    }
}

