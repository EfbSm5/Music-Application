package com.example.mymusicapplication.ui.editUserPage

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mymusicapplication.EditUserProfileViewModel
import com.example.mymusicapplication.UserProfile
import com.example.mymusicapplication.database.toJson


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ShowAll(
    viewModel: EditUserProfileViewModel, navigateToHomePage: () -> Unit
) {
    val context = LocalContext.current
    var finished by remember { mutableStateOf(false) }
    ShowAllScreen(userProfile = viewModel.profile.value, context = context) {
        finished = true
    }
    if (finished) {
        viewModel.insertDataToDataBase(context = context)
        navigateToHomePage()
    }
}

@Composable
private fun ShowAllScreen(
    userProfile: UserProfile, context: Context, onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.padding(30.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center
        ) {
            item { Spacer(modifier = Modifier.height(80.dp)) }
            item {
                ShowNameAndAvatar(userProfile = userProfile)
                Divider(color = Color.Gray, thickness = 1.dp)
            }
            item {
                ShowSex(userProfile = userProfile)
                Divider(color = Color.Gray, thickness = 1.dp)
            }
            item {
                ShowBirthDay(userProfile = userProfile)
                Divider(color = Color.Gray, thickness = 1.dp)
            }
            item {
                ShowPreference(userProfile = userProfile)
                Divider(color = Color.Gray, thickness = 1.dp)
            }
            item {
                DataButtons(context = context, userProfile = userProfile, onClick = { onClick() })
            }
        }
    }
}

@Composable
private fun ShowNameAndAvatar(userProfile: UserProfile) {
    Row(
        modifier = Modifier.height(50.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Filled.AccountBox, contentDescription = null, Modifier.size(50.dp)
        )
        Text(text = "昵称:", style = TextStyle(fontSize = 30.sp))
        Text(
            text = userProfile.name.ifEmpty { "默认昵称" }, style = TextStyle(fontSize = 30.sp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .size(50.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            AsyncImage(
                model = userProfile.photoFile,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
            )
        }
    }
}

@Composable
private fun ShowSex(userProfile: UserProfile) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Face, contentDescription = null, Modifier.size(50.dp)
        )
        Text(text = "性别:", style = TextStyle(fontSize = 30.sp))
        Text(text = userProfile.sex, style = TextStyle(fontSize = 30.sp))
    }
}

@Composable
private fun ShowBirthDay(userProfile: UserProfile) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Filled.DateRange, contentDescription = null, Modifier.size(50.dp)
        )
        Text(text = "出生日期:", style = TextStyle(fontSize = 30.sp))
        Text(text = userProfile.birthDay, style = TextStyle(fontSize = 20.sp))
    }
}

@Composable
private fun ShowPreference(userProfile: UserProfile) {
    Row(
        modifier = Modifier.height(50.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite, contentDescription = null, Modifier.size(50.dp)
        )
        Text(text = "喜好:", style = TextStyle(fontSize = 30.sp))

        Text(
            text = if (userProfile.preference.isNotEmpty()) userProfile.preference.joinToString(
                separator = " "
            ) else "无",
            style = TextStyle(fontSize = 20.sp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

    }
}

@Composable
private fun DataButtons(
    context: Context, userProfile: UserProfile, onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            onClick()
        }) {
            Text(text = "保存")
        }
        Spacer(modifier = Modifier.width(20.dp))
        Button(onClick = {
            uploadDataToClipBoard(context = context, userProfile = userProfile)
        }) {
            Text(text = "输入剪切板")
        }
    }
}


fun uploadDataToClipBoard(context: Context, userProfile: UserProfile) {
    val jsonData = toJson(userProfile)
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", jsonData)
    clipboard.setPrimaryClip(clip)
}
