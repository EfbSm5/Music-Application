package com.example.mymusicapplication.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.mymusicapplication.UserProfile
import com.example.mymusicapplication.database.AppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var profileInDataBase by remember { mutableStateOf<List<UserProfile>?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var profileShow by remember {
        mutableStateOf<UserProfile?>(null)
    }
    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            profileInDataBase = AppDataBase.getDatabase(context).userDao().loadAllUsers()
        }
    }
    if (profileInDataBase != null && profileInDataBase!!.isNotEmpty()) {
        if (profileInDataBase!!.size > 1) {
            SingleChoiceDialog(openDialog = profileShow == null,
                options = profileInDataBase!!,
                onClick = { profileShow = it },
                {})
        } else {
            profileShow = profileInDataBase!![0]
        }
    } else {
        Text(text = "没有数据")
    }
    profileShow?.let { ShowUserProfile(userProfile = it) }
}


@Composable
private fun SingleChoiceDialog(
    openDialog: Boolean,
    options: List<UserProfile>,
    onClick: (UserProfile) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (openDialog) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Surface(color = MaterialTheme.colorScheme.primaryContainer) {
                LazyColumn {
                    item {
                        Text(
                            text = "选择一个账户", modifier = Modifier.padding(16.dp)
                        )
                    }
                    itemsIndexed(options) { _, option ->
                        Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onClick(option) }
                                .padding(horizontal = 16.dp, vertical = 8.dp)) {
                            Text(
                                text = option.name, modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ShowUserProfile(userProfile: UserProfile) {
    Surface(
        Modifier.padding(30.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center
        ) {
            item { Spacer(modifier = Modifier.height(80.dp)) }
            item {
                Row(
                    modifier = Modifier.height(50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.AccountBox, contentDescription = null, Modifier.size(50.dp)
                    )
                    androidx.compose.material3.Text(
                        text = "昵称:", style = TextStyle(fontSize = 30.sp)
                    )
                    androidx.compose.material3.Text(
                        text = userProfile.name, style = TextStyle(fontSize = 30.sp)
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
                TabRowDefaults.Divider(color = Color.Gray, thickness = 1.dp)
            }
            item {
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Face,
                        contentDescription = null,
                        Modifier.size(50.dp)
                    )
                    androidx.compose.material3.Text(
                        text = "性别:", style = TextStyle(fontSize = 30.sp)
                    )
                    androidx.compose.material3.Text(
                        text = userProfile.sex, style = TextStyle(fontSize = 30.sp)
                    )
                }
                TabRowDefaults.Divider(color = Color.Gray, thickness = 1.dp)
            }
            item {
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = null,
                        Modifier.size(50.dp)
                    )
                    androidx.compose.material3.Text(
                        text = "出生日期:", style = TextStyle(fontSize = 30.sp)
                    )
                    androidx.compose.material3.Text(
                        text = userProfile.birthDay, style = TextStyle(fontSize = 20.sp)
                    )
                }
                TabRowDefaults.Divider(color = Color.Gray, thickness = 1.dp)
            }
            item {
                Row(
                    modifier = Modifier.height(50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        Modifier.size(50.dp)
                    )
                    androidx.compose.material3.Text(
                        text = "喜好:", style = TextStyle(fontSize = 30.sp)
                    )

                    androidx.compose.material3.Text(
                        text = if (userProfile.preference.isEmpty()) userProfile.preference.joinToString(
                            separator = " "
                        ) else "无", style = TextStyle(fontSize = 30.sp)
                    )

                }
                TabRowDefaults.Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
    }
}

