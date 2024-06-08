package com.example.mymusicapplication.ui.editUserPage

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun PreviewEditName() {
    EditName {

    }
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
        item {
            Text(
                text = "请输入你的昵称",
                style = TextStyle(fontSize = 25.sp),
                color = MaterialTheme.colorScheme.primary
            )
        }
        item {
            TextField(
                value = name,
                onValueChange = { newText -> name = newText }
            )
        }
    }
}