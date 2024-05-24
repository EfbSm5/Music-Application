package com.example.mymusicapplication.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditName(needName: MutableList<String>, onClick: (String) -> Unit = {}) {
    var name by remember { mutableStateOf("") }
    var isVisible by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier.padding(50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "请输入你的名字")
        TextField(
            value = name,
            onValueChange = { newText -> name = newText },
            label = { Text("请输入姓名") })
        Button(onClick = {
            onClick(name)

        }) {
            Text(text = "下一题")
        }
    }
}