package com.example.mymusicapplication

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymusicapplication.ui.editUserPage.EditProfileContents
import com.example.mymusicapplication.ui.editUserPage.EditProfileScreen
import com.example.mymusicapplication.ui.editUserPage.lastScreen
import com.example.mymusicapplication.ui.editUserPage.nextScreen
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
            EditProfileScreen()
        }
    }
}



