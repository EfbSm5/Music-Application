package com.example.mymusicapplication.ui.page

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymusicapplication.database.AppDataBase
import com.example.mymusicapplication.UserProfile
import com.example.mymusicapplication.database.toJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ShowAll(userProfile: UserProfile, context: Context) {

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            LazyRow {
                item { Text(text = "名字:") }
                item { Text(text = userProfile.name) }
                item {
                    AsyncImage(
                        model = userProfile.photoFile,
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
        }
        item {
            Row {
                Text(text = "性别:")
                Text(text = userProfile.sex)
            }
        }
        item {
            Row {
                Text(text = "出生日期:")
                Text(text = userProfile.birthDay)
            }
        }
        item {
            Row {
                Text(text = "喜好:")

                if (userProfile.preference.isEmpty()) {
                    Text(text = "无")
                } else {
                    Text(text = userProfile.preference.joinToString(separator = " "))
                }

            }
        }
        item {
            Button(onClick = {
                uploadData(context, userProfile)
            }) {
                Text(text = "确定")
            }
        }
    }
}

fun uploadData(context: Context, userProfile: UserProfile) {
    val jsonData = toJson(userProfile)
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", jsonData)
    clipboard.setPrimaryClip(clip)
    val userDao = AppDataBase.getDatabase(context).userDao()
    CoroutineScope(Dispatchers.IO).launch {
        userDao.insertData(userProfile, context)
    }
}
