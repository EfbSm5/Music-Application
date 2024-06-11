package com.example.mymusicapplication.ui.editUserPage

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicapplication.EditUserProfileViewModel


@Composable
fun EditName(
    viewModel: EditUserProfileViewModel, saveData: (String) -> Unit
) {
    EditNameScreen(viewModel = viewModel)
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun EditNameScreen(viewModel: EditUserProfileViewModel) {
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
                value = if (viewModel.profile.value.name != "默认昵称") viewModel.profile.value.name else "",
                onValueChange = { newText -> viewModel.updateName(newText) })
        }
    }
}