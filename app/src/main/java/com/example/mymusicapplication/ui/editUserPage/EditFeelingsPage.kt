package com.example.mymusicapplication.ui.editUserPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditEmotion(saveData: (Float) -> Unit) {
    var feeling by remember { mutableFloatStateOf(0.0F) }
    DisposableEffect(Unit) {
        onDispose {
            saveData(feeling)
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Text(text = "你更喜欢更轻柔的音乐还是更有力量的音乐") }
        item { Spacer(modifier = Modifier.height(200.dp)) }
        item {
            Slider(
                value = feeling,
                onValueChange = { feeling = it },
                steps = 4
            )
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
    }
}