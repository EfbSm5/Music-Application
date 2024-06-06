package com.example.mymusicapplication.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymusicapplication.UserProfile
import com.example.mymusicapplication.database.checkDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var profileInDataBase by remember { mutableStateOf<UserProfile?>(null) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            checkDataBase(context) { profileInDataBase = it }
        }
    }
    if (profileInDataBase != null) {
        ShowUserProfile(userProfile = profileInDataBase!!)
    } else {
        Text(text = "数据加载中...")
    }
}
@Composable
fun ShowUserProfile(userProfile: UserProfile) {
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
    }
}

