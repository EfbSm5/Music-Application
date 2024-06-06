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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mymusicapplication.database.toProfile
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
                    NavGraph()
                }
            }
        }

    }
    private fun checkClipBoard(context: Context): UserProfile? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return if (clipboard.hasPrimaryClip()) {
            val clipData = clipboard.primaryClip
            if (clipData != null) {
                val item = clipData.getItemAt(0)
                toProfile(item.text.toString())
            } else null
        } else null
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

}









