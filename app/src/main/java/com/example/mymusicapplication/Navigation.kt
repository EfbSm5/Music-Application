package com.example.mymusicapplication

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymusicapplication.ui.editUserPage.EditProfile
import com.example.mymusicapplication.ui.page.HomeScreen
import com.example.mymusicapplication.ui.page.Welcome

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = "WelcomePage",
        modifier = Modifier.fillMaxSize()
    ) {
        composable("WelcomePage") {
            Welcome(
                editUserProfile = { navController.navigate("EditPage") },
                login = { navController.navigate("HomePage") },
            )
        }
        composable("HomePage") {
            HomeScreen()
        }
        composable("EditPage") {
            EditProfile {
                navController.navigate("HomePage")
            }
        }
    }
}



