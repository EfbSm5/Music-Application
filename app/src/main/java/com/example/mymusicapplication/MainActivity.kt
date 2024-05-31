package com.example.mymusicapplication

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mymusicapplication.ui.page.MainPage
import com.example.mymusicapplication.ui.page.UserConfigurationInitializationPage
import com.example.mymusicapplication.ui.theme.MyMusicApplicationTheme

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
                    val tempProfile = checkClipBoard()
                    val havingProfile by remember { mutableStateOf(tempProfile.name != "") }
                    var confirmed by remember { mutableStateOf(false) }
                    var openNewDialog by rememberSaveable { mutableStateOf(true) }
                    DialogForHavingProfile(openDialog = havingProfile, confirm = {  }) {}
                    if (!confirmed) {
                        MainPage()
                    } else {
                        GetProfile()
                    }
                    DialogForNewProfile(openDialog = openNewDialog, confirm = {
                        confirmed = true
                        openNewDialog = false
                    }, onDismissRequest = { openNewDialog = false })
                }
            }
        }

    }

    private fun checkClipBoard(): UserProfile {
        // 获取剪切板管理器
        var text = ""
        val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboard.hasPrimaryClip()) {
            val clipData = clipboard.primaryClip
            val item = clipData?.getItemAt(0)
            text = item?.text.toString()
        }
        val temp = fromJson(text)
        return temp
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
}

@Composable
fun DialogForHavingProfile(
    openDialog: Boolean, confirm: () -> Unit, onDismissRequest: () -> Unit
) {
    if (openDialog) {
        AlertDialog(onDismissRequest = onDismissRequest,
            icon = { Icon(Icons.Filled.Edit, contentDescription = null) },
            title = {
                Text(text = "我们发现你的剪切板有我们想要的数据")
            },
            text = {
                Text(
                    "想要直接导入吗"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    confirm()
                }) {
                    Text("我没意见")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismissRequest()
                }) {
                    Text("我有意见")
                }
            })
    }
}

@Composable
fun DialogForNewProfile(openDialog: Boolean, confirm: () -> Unit, onDismissRequest: () -> Unit) {
    if (openDialog) {
        AlertDialog(onDismissRequest = onDismissRequest,
            icon = { Icon(Icons.Filled.Edit, contentDescription = null) },
            title = {
                Text(text = "欢迎使用Music App")
            },
            text = {
                Text(
                    "我们发现你尚未注册，想要让我们获取你的个人信息吗"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    confirm()
                }) {
                    Text("我没意见")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismissRequest()
                }) {
                    Text("我有意见")
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
            UserConfigurationInitializationPage()
        }
    }
}




