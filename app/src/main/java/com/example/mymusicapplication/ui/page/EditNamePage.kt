package com.example.mymusicapplication.ui.page

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext

@Composable
fun EditName(onNameConfirmed: (String) -> Unit, onNavigateToNextScreen: () -> Unit = {}) {
    var name by remember { mutableStateOf("") }
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Text(text = "请输入你的名字") }
        item {
            TextField(value = name,
                onValueChange = { newText -> name = newText },
                label = { Text("请输入姓名") })
        }
        item {
            Button(onClick = {
                if (name.isNotEmpty()) {
                    onNameConfirmed(name)
                    onNavigateToNextScreen()
                } else {
                    Toast.makeText(context, "使用默认昵称", Toast.LENGTH_SHORT).show()
                    onNameConfirmed("默认昵称")
                    onNavigateToNextScreen()
                }
            }) {
                Text(text = "下一题")
            }
        }
    }
}