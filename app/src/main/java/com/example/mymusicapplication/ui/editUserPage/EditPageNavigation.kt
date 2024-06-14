package com.example.mymusicapplication.ui.editUserPage

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mymusicapplication.EditUserProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(navControllerForHome: NavController) {
    var state: State by remember { mutableStateOf(State.Name) }
    val viewModel by remember { mutableStateOf(EditUserProfileViewModel()) }
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "让我们更了解你", style = TextStyle(fontSize = 30.sp)
            )
//            LinearProgressIndicator(
//                progress = (),
//                modifier = Modifier.fillMaxWidth(),
//            )
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
                if (state != State.Name) {
                    OutlinedButton(
                        onClick = {
                            state = state.lastScreen()
                        }, modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "上一题")
                    }
                } else Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(12.dp))
                if (state != State.Data) {
                    Button(
                        onClick = {
                            state = state.nextScreen()
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
            UserConfigurationInitializationPage(
                navControllerForHome = navControllerForHome,
                currentScreen = state,
                viewModel = viewModel
            )
        }
    }

}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UserConfigurationInitializationPage(
    navControllerForHome: NavController, currentScreen: State, viewModel: EditUserProfileViewModel
) {
    val context = LocalContext.current
    Crossfade(targetState = currentScreen, label = "") { screen ->
        when (screen) {
            State.Name -> {
                EditName(viewModel = viewModel)
            }

            State.Sex -> {
                EditSex(viewModel = viewModel)
            }

            State.Avatar -> {
                EditAvatar(viewModel = viewModel)
            }

            State.Birthday -> {
                EditBirthDay(viewModel = viewModel)
            }

            State.FloatValue -> {
                EditEmotion(viewModel = viewModel)
            }

            State.Preference -> {
                EditPreference(viewModel = viewModel)
            }

            State.Data -> {
                ShowAll(
                    viewModel, context = context, navControllerForHome = navControllerForHome
                )
            }

            else -> {
            }
        }
    }
}

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

    fun lastScreen(): State {
        return list[list.indexOf(this) - 1]
    }
}


