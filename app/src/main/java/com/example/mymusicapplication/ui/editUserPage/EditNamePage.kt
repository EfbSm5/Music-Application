package com.example.mymusicapplication.ui.editUserPage

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewEditName() {
}

@Composable
fun EditName(
    saveData: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    DisposableEffect(Unit) {
        onDispose {
            saveData(name.ifEmpty {
                "默认昵称"
            })
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Spacer(modifier = Modifier.height(200.dp)) }
        item { Text(text = "请输入你的名字") }
        item {
            TextField(value = name,
                onValueChange = { newText -> name = newText },
                label = { Text("请输入姓名") })
        }
    }
}