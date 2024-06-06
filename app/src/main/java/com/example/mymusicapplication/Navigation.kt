package com.example.mymusicapplication

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymusicapplication.ui.editUserPage.EditProfileScreen
import com.example.mymusicapplication.ui.page.HomeScreen
import com.example.mymusicapplication.ui.page.Welcome

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NavGraph() {
    val navControllerAtWelcomePage = rememberNavController()
    NavHost(navControllerAtWelcomePage, startDestination = "WelcomePage") {
        composable("WelcomePage") {
            Welcome({ navControllerAtWelcomePage.navigate("EditPage") },
                { navControllerAtWelcomePage.navigate("HomePage") })
        }
        composable("HomePage") {
            HomeScreen()
        }
        composable("EditPage") {
            EditProfileScreen(navControllerAtWelcomePage)
        }
    }
}



