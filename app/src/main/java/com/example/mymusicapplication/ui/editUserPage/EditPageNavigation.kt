package com.example.mymusicapplication.ui.editUserPage

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymusicapplication.EditUserProfileViewModel
import com.example.mymusicapplication.QuestionsAndAnswers
import com.example.mymusicapplication.ui.page.ShowAll


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen() {
    val navController = rememberNavController()
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "让我们更了解你") })
    }, bottomBar = {
        BottomAppBar {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    lastScreen(navController)
                }) {
                    Text(text = "上一题")
                }
                Button(onClick = {
                    nextScreen(navController)
                }) {
                    Text(text = "下一题")
                }
            }
        }
    }) {
        Box(
            modifier = Modifier.padding(it), contentAlignment = Alignment.Center
        ) {
            EditProfileContents(navController)
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditProfileContents(navController: NavHostController) {
    val sexQuestionsAndAnswers = QuestionsAndAnswers("你的性别", listOf("男", "女", "其他"))
    val preferencesQuestionsAndAnswers = QuestionsAndAnswers(
        "你听歌的偏好", listOf("民谣", "摇滚", "流行", "说唱", "电子", "ACG", "古典", "爵士")
    )
    val viewModel = EditUserProfileViewModel()
    NavHost(navController, startDestination = "editName") {
        composable("editName") {
            EditName { viewModel.updateName(it) }
        }
        composable("editSex") {
            EditSex(sexQuestionsAndAnswers) { viewModel.updateSex(it) }
        }
        composable("editBirthday") {
            EditBirthDay { viewModel.updateBirthday(it) }
        }
        composable("editPreferences") {
            EditPreference(preferencesQuestionsAndAnswers) { viewModel.updatePreferences(it) }
        }
        composable("editEmotion") {
            EditEmotion { viewModel.updateEmotion(it) }
        }
        composable("editAvatar") {
            PhotoScreen { viewModel.updateAvatar(it) }
        }
        composable("summary") {
            ShowAll(viewModel.profile.value, LocalContext.current)
        }
    }
}


fun nextScreen(navController: NavController) {
    val list = listOf(
        "editName",
        "editSex",
        "editBirthday",
        "editPreferences",
        "editEmotion",
        "editAvatar",
        "summary"
    )
    val currentDestination = navController.currentBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val index = list.indexOf(currentRoute)
    val nextRoute = if (index in 0 until list.lastIndex) {
        list[index + 1]
    } else null
    if (nextRoute != null) {
        navController.navigate(nextRoute)
    }
}

fun lastScreen(navController: NavController) {
    val list = listOf(
        "editName",
        "editSex",
        "editBirthday",
        "editPreferences",
        "editEmotion",
        "editAvatar",
        "summary"
    )
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
