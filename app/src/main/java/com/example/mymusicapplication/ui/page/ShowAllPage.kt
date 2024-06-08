package com.example.mymusicapplication.ui.page

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mymusicapplication.database.AppDataBase
import com.example.mymusicapplication.UserProfile
import com.example.mymusicapplication.database.toJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
@Preview
fun ShowAllPreview() {
    ShowAll(userProfile = UserProfile(), context = LocalContext.current)
}


@Composable
fun ShowAll(userProfile: UserProfile, context: Context) {

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Spacer(modifier = Modifier.height(80.dp)) }
        item {
            LazyRow(modifier = Modifier.height(80.dp)) {
                item { Text(text = "名字:", style = TextStyle(fontSize = 50.sp)) }
                item { Text(text = userProfile.name, style = TextStyle(fontSize = 50.sp)) }
                item {
                    AsyncImage(
                        model = userProfile.photoFile,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }
        item {
            Row(modifier = Modifier.height(80.dp)) {
                Text(text = "性别:", style = TextStyle(fontSize = 50.sp))
                Text(text = userProfile.sex, style = TextStyle(fontSize = 50.sp))
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }
        item {
            Row(modifier = Modifier.height(100.dp)) {
                Text(text = "出生日期:", style = TextStyle(fontSize = 50.sp))
                Text(text = userProfile.birthDay, style = TextStyle(fontSize = 40.sp))
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }
        item {
            Row(modifier = Modifier.height(100.dp)) {
                Text(text = "喜好:", style = TextStyle(fontSize = 50.sp))
                if (userProfile.preference.isEmpty()) {
                    Text(text = "无", style = TextStyle(fontSize = 50.sp))
                } else {
                    Text(
                        text = userProfile.preference.joinToString(separator = " "),
                        style = TextStyle(fontSize = 50.sp)
                    )
                }
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }
        item {
            Button(onClick = {
                uploadDataToDataBase(context, userProfile)
            }) {
                Text(text = "保存")
            }
        }
        item {
            Button(onClick = {
                uploadDataToClipBoard(context, userProfile)
            }) {
                Text(text = "输入剪切板")
            }
        }
    }
}

fun uploadDataToDataBase(context: Context, userProfile: UserProfile) {
    val userDao = AppDataBase.getDatabase(context).userDao()
    CoroutineScope(Dispatchers.IO).launch {
        userDao.insertData(userProfile, context)
    }
}

fun uploadDataToClipBoard(context: Context, userProfile: UserProfile) {
    val jsonData = toJson(userProfile)
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", jsonData)
    clipboard.setPrimaryClip(clip)
}
