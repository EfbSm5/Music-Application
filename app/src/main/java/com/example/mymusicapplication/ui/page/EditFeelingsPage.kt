package com.example.mymusicapplication.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mymusicapplication.EditUserProfileViewModel
import com.example.mymusicapplication.lastScreen
import com.example.mymusicapplication.nextScreen

@Composable
fun EditEmotion(viewModel: EditUserProfileViewModel, navController: NavController) {
    var feeling by remember { mutableFloatStateOf(0.0F) }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Text(text = "你更喜欢更轻柔的音乐还是更有力量的音乐") }
        item {
            Slider(value = feeling, onValueChange = { feeling = it })
        }
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                item { Text(text = "更轻柔") }
                item { Text(text = "都行") }
                item { Text(text = "更有节奏感") }
            }
        }
        item {
            Row {
                Button(onClick = {
                    viewModel.updateEmotion(feeling)
                    nextScreen(navController)
                }) {
                    Text(text = "确定")
                }
                Button(onClick = {
                    lastScreen(navController)
                }) {
                    Text(text = "返回")
                }
            }
        }

    }

}