package com.example.mymusicapplication.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymusicapplication.UserProfile
import java.io.File.separator

@Composable
fun ShowAll(userProfile: UserProfile) {
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
            LazyRow {
                item { Text(text = "性别:") }
                item { Text(text = userProfile.sex) }
            }
        }
        item {
            LazyRow {
                item { Text(text = "出生日期:") }
                item { Text(text = userProfile.birthDay) }
            }
        }
        item {
            LazyRow {
                item { Text(text = "喜好:") }
                item {
                    if (userProfile.preference.isNullOrEmpty()) {
                        Text(text = "无")
                    } else {
                        Text(text = userProfile.preference.joinToString(separator = " "))
                    }
                }
            }
        }

    }
}