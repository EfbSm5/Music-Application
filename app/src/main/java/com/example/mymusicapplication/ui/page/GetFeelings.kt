package com.example.mymusicapplication.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun GetFeelings(feel: MutableList<Float>) {
    val feeling = remember { mutableStateOf<Float>(0.0F) }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Slider(value = feeling.value, onValueChange = { feeling.value = it })
        }
        item { Text(text = feeling.value.toString()) }
        item {
            Button(onClick = { feel.add(feeling.value) }) {

            }
        }

    }

}