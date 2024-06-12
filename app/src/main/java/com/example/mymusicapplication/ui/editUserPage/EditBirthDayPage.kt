package com.example.mymusicapplication.ui.editUserPage

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicapplication.EditUserProfileViewModel
import com.example.mymusicapplication.UserProfile
import java.text.SimpleDateFormat
import java.util.Calendar


@SuppressLint("SimpleDateFormat")
@Composable
fun EditBirthDay(viewModel: EditUserProfileViewModel) {
    val profile by viewModel.profile.collectAsState()
    val context = LocalContext.current
    EditBirthDayScreen(profile) {
        showDatePicker(
            context = context, viewModel = viewModel, profile = profile
        )
    }
}

@Composable
private fun EditBirthDayScreen(
    profile: UserProfile, openDialog: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface, modifier = Modifier.padding(30.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Spacer(modifier = Modifier.height(200.dp)) }
            item {
                Text(
                    text = "请选择你的生日",
                    style = TextStyle(fontSize = 30.sp),
                    color = MaterialTheme.colorScheme.outline
                )
            }
            item { Text(text = "你选择的是   " + profile.birthDay) }
            item { Spacer(modifier = Modifier.height(200.dp)) }
            item {
                Button(onClick = {
                    openDialog()
                }) {
                    Text("选择日期")
                }
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
private fun showDatePicker(
    context: Context, viewModel: EditUserProfileViewModel, profile: UserProfile
) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val calendar = Calendar.getInstance()
    calendar.time = dateFormat.parse(profile.birthDay)!!
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            viewModel.updateBirthday(dateFormat.format(calendar.time))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}