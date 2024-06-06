package com.example.mymusicapplication.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Welcome(editUserProfile: () -> Unit, login: () -> Unit) {
    Column {
        Text(
            text = "开发中", modifier = Modifier.width(200.dp), textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(100.dp))
        Button(
            onClick = { editUserProfile() }, modifier = Modifier.width(200.dp)
        ) {
            Text(
                text = "进入编辑账户界面",
                modifier = Modifier.width(400.dp),
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = { login() }, modifier = Modifier.width(200.dp)
        ) {
            Text(
                text = "登录",
                modifier = Modifier.width(200.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
