package com.example.mymusicapplication.ui.editUserPage

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymusicapplication.EditUserProfileViewModel
import com.example.mymusicapplication.QuestionsAndAnswers


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(navControllerForHome: NavController) {
    val shouldShowLast = remember { mutableStateOf(true) }

    val shouldShowNext = remember { mutableStateOf(true) }

    val navController = rememberNavController()
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "让我们更了解你", style = TextStyle(fontSize = 30.sp)
            )
        })
    }, bottomBar = {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (shouldShowLast.value) {
                    Button(
                        onClick = {
                            lastScreen(navController, navControllerForHome)
                        }, modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "上一题")
                    }
                } else Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(12.dp))
                if (shouldShowNext.value) {
                    Button(
                        onClick = {
                            nextScreen(navController, navControllerForHome)
                        }, modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "下一题")
                    }
                } else Spacer(modifier = Modifier.weight(1f))
            }
        }
    }) {
        Box(
            modifier = Modifier.padding(it),
            contentAlignment = Alignment.Center,
        ) {
            EditProfileContents(
                navController = navController,
                showLastButton = { shouldShowLast.value = it },
                showNextButton = { shouldShowNext.value = it },
                navControllerForHome = navControllerForHome
            )
        }
    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun EditProfileContents(
    navController: NavHostController,
    showLastButton: (Boolean) -> Unit,
    showNextButton: (Boolean) -> Unit,
    navControllerForHome: NavController
) {
    val sexQuestionsAndAnswers = QuestionsAndAnswers("你的性别", listOf("男", "女", "其他"))
    val viewModel = EditUserProfileViewModel()

    NavHost(navController, startDestination = "editName") {
        composable("editName") {
            EditName(viewModel = viewModel)
            showLastButton(false)
            showNextButton(true)
        }
        composable("editSex") {
            EditSex(questionsAndAnswers = sexQuestionsAndAnswers, viewModel = viewModel)
            showLastButton(true)
            showNextButton(true)
        }
        composable("editBirthday") {
            EditBirthDay(viewModel = viewModel)
            showLastButton(true)
            showNextButton(true)
        }
        composable("editPreferences") {
            EditPreference(viewModel = viewModel)
            showLastButton(true)
            showNextButton(true)
        }
        composable("editEmotion") {
            EditEmotion(viewModel = viewModel) { viewModel.updateEmotion(it) }
            showLastButton(true)
            showNextButton(true)
        }
        composable("editAvatar") {
            EditAvator { viewModel.updateAvatar(it) }
            showLastButton(true)
            showNextButton(true)
        }
        composable("summary") {
            ShowAll(
                userProfile = viewModel.profile.value,
                context = LocalContext.current,
                navControllerForHome = navControllerForHome
            )
            showNextButton(false)
            showLastButton(true)
        }
    }
}


private fun nextScreen(navController: NavController, navControllerForHome: NavController) {
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
    } else {
        navControllerForHome.navigate("HomePage")
    }
}

private fun lastScreen(navController: NavController, navControllerForHome: NavController) {
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
    val lastRoute = if (index > 0) {
        list[index - 1]
    } else {
        null
    }
    if (lastRoute != null) {
        navController.navigate(lastRoute)
    } else {
        navControllerForHome.navigate("WelcomePage")
    }
}
