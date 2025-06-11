package com.example.mymusicapplication.ui.editUserPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicapplication.EditUserProfileViewModel
import com.example.mymusicapplication.UserProfile


@Composable
fun EditName(viewModel: EditUserProfileViewModel) {
    val profile by viewModel.profile.collectAsState()
    EditNameScreen(viewModel = viewModel, profile)
}

@Composable
private fun EditNameScreen(viewModel: EditUserProfileViewModel, profile: UserProfile) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Text(
            text = "请输入你的昵称",
            style = TextStyle(fontSize = 25.sp),
            color = MaterialTheme.colorScheme.primary
        )
        TextField(
            value = if (profile.name != "默认昵称") profile.name else "",
            onValueChange = { newText -> viewModel.updateName(newText) })
    }
}