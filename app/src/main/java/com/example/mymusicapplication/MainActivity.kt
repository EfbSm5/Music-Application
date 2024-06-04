package com.example.mymusicapplication

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mymusicapplication.database.checkDataBase
import com.example.mymusicapplication.database.insertDataBase
import com.example.mymusicapplication.database.toProfile
import com.example.mymusicapplication.ui.page.ShowAll
import com.example.mymusicapplication.ui.page.UserConfigurationInitializationPage
import com.example.mymusicapplication.ui.theme.MyMusicApplicationTheme
import kotlinx.coroutines.runBlocking

private const val TAG = "MainActivity"

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
                    Display()
                }
            }
        }

    }

    @Composable
    private fun Display() {
        var checkedClipBoard by remember { mutableStateOf(false) }
        val profile = runBlocking { checkDataBase(applicationContext) }
        var havingProfile by remember { mutableStateOf(profile != null) }
        CheckClipBoard(LocalContext.current) { checkedClipBoard = true }
        if (checkedClipBoard) {
            if (havingProfile) {
                ShowAll(profile!!, LocalContext.current)
            } else {
                GetProfile { havingProfile = true }
            }
        }
    }

    @Composable
    private fun CheckClipBoard(context: Context, noData: () -> Unit) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val userProfile: UserProfile? = if (clipboard.hasPrimaryClip()) {
            val clipData = clipboard.primaryClip
            val item = clipData?.getItemAt(0)
            toProfile(item?.text.toString())
        } else null
        var havingInClipBoard by remember { mutableStateOf(userProfile != null) }
        var inserted by remember { mutableStateOf(false) }
        Log.d(TAG, "GetProfile:                                         $havingInClipBoard")
        if (havingInClipBoard) {
            DialogForHavingProfile(true, confirm = {
                insertDataBase(context, userProfile!!)
                inserted = true
                havingInClipBoard = false
            }, onDismissRequest = {
                havingInClipBoard = false
            })
        } else {
            noData()
        }
        if (inserted) {
            ShowAll(userProfile = userProfile!!, context = context)
        }
    }

    @Composable
    private fun DialogForHavingProfile(
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
    private fun DialogForNewProfile(
        openDialog: Boolean, confirm: () -> Unit, onDismissRequest: () -> Unit
    ) {
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
    @Composable
    private fun GetProfile(confirm: () -> Unit) {
        var confirmed by remember { mutableStateOf(false) }
        var openNewDialog by rememberSaveable { mutableStateOf(true) }
        DialogForNewProfile(openDialog = openNewDialog, confirm = {
            confirmed = true
            openNewDialog = false
        }, onDismissRequest = {
            openNewDialog = false
            confirm()
        })
        if (confirmed) {
            UserConfigurationInitializationPage()
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
}






