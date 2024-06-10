package com.example.mymusicapplication.ui.editUserPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicapplication.EditUserProfileViewModel



@Composable
fun EditEmotion(viewModel: EditUserProfileViewModel, saveData: (Float) -> Unit) {
    var feeling by remember { mutableFloatStateOf(viewModel.profile.value.useEmotion) }
    DisposableEffect(Unit) {
        onDispose {
            saveData(feeling)
        }
    }
    EditEmotionScreen(feeling = feeling) {
        feeling = it
    }
}

@Composable
fun EditEmotionScreen(feeling: Float, onSlide: (Float) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Spacer(modifier = Modifier.height(200.dp)) }
        item {
            Text(
                text = "你更喜欢更轻柔的音乐还是更有力量的音乐",
                style = TextStyle(fontSize = 20.sp),
                color = MaterialTheme.colorScheme.outline
            )
        }
        item { Spacer(modifier = Modifier.height(200.dp)) }
        item {
            Slider(
                value = feeling,
                onValueChange = { onSlide(it) },
                steps = 3
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "更轻柔")
                Text(text = "都行")
                Text(text = "更热烈")
            }
        }
    }
}