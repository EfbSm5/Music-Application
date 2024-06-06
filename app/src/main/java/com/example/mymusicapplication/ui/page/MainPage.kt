package com.example.mymusicapplication.ui.page

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MainPage() {
    Row {
        Text(text = "开发中")
        Button(onClick = { /*TODO*/ }) {
            Text(text = "进入编辑账户界面")
        }
    }
}