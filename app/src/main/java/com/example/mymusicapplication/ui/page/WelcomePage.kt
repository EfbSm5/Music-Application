package com.example.mymusicapplication.ui.page

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicapplication.UserProfile
import com.example.mymusicapplication.database.insertDataBase
import com.example.mymusicapplication.database.toProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun Welcome(editUserProfile: () -> Unit, login: () -> Unit) {
    var openDialog by rememberSaveable { mutableStateOf(true) }
    CheckClipBoardAndDialog(openDialog = openDialog) { openDialog = false }
    WelcomeScreen(editUserProfile = editUserProfile) { login() }
}

@Composable
private fun WelcomeScreen(editUserProfile: () -> Unit, login: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Column {
            Text(
                text = "开发中",
                modifier = Modifier.width(200.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 40.sp),
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(100.dp))
            Button(
                onClick = { editUserProfile() }, modifier = Modifier.width(200.dp)
            ) {
                Text(
                    text = "进入编辑账户界面",
                    modifier = Modifier.width(400.dp),
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = { login() }, modifier = Modifier.width(200.dp)
            ) {
                Text(
                    text = "登录", modifier = Modifier.width(200.dp), textAlign = TextAlign.Center
                )
            }
        }
    }
}

private fun checkClipBoard(context: Context, callBack: (UserProfile?) -> Unit) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    callBack(
        if (clipboard.hasPrimaryClip()) {
            val clipData = clipboard.primaryClip
            if (clipData != null) {
                val item = clipData.getItemAt(0)
                toProfile(item.text.toString())
            } else null
        } else null
    )
}
@Preview
@Composable
fun preview11(){
    DialogForHavingProfile(openDialog = true, confirm = { /*TODO*/ }) {
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
private fun CheckClipBoardAndDialog(openDialog: Boolean, callBack: () -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var profile by remember { mutableStateOf<UserProfile?>(null) }
    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            checkClipBoard(context) { profile = it }
        }
    }
    if (profile != null) {
        DialogForHavingProfile(openDialog = openDialog, confirm = {
            callBack()
            insertDataBase(context, profile!!)
        }, onDismissRequest = {
            callBack()
        })
    }
}

