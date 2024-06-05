package com.example.mymusicapplication.ui.page

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.inputmethodservice.Keyboard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mymusicapplication.EditUserProfileViewModel
import com.example.mymusicapplication.lastScreen
import com.example.mymusicapplication.nextScreen
import java.text.SimpleDateFormat
import java.util.Calendar

@SuppressLint("SimpleDateFormat")
@Composable
fun EditBirthDay(viewModel: EditUserProfileViewModel, navController: NavController) {

    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val context = LocalContext.current
    val dataFormat = SimpleDateFormat("yyyy-MM-dd")

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Text(text = "请选择你的生日") }
        item { Text(dataFormat.format(selectedDate.time)) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Button(onClick = {
                showDatePicker(context, selectedDate) { newDate ->
                    selectedDate = newDate
                }
            }) {
                Text("Select Date")
            }
        }
        item {
            Row {
                Button(onClick = {
                    viewModel.updateBirthday(dataFormat.format(selectedDate.time))
                    nextScreen(navController)
                }) {
                    Text(text = "确定")
                }
                Button(onClick = {
                    lastScreen(navController)
                }) {
                    Text(text = "返回")
                }
            }
        }
    }
}

private fun showDatePicker(
    context: android.content.Context, selectedDate: Calendar, onDateSelected: (Calendar) -> Unit
) {
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            onDateSelected(calendar)
        },
        selectedDate.get(Calendar.YEAR),
        selectedDate.get(Calendar.MONTH),
        selectedDate.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}