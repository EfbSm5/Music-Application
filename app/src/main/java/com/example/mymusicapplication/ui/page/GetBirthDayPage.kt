package com.example.mymusicapplication.ui.page

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import java.util.Calendar

@Composable
fun GetBirthDay(birthDay: MutableList<String>) {

    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.padding(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Text("Selected Date: ${selectedDate.time}") }
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
            Button(onClick = { birthDay.add(selectedDate.toString()) }) {
                Text(text = "确定")
            }
        }
    }

}


private fun showDatePicker(
    context: android.content.Context,
    selectedDate: Calendar,
    onDateSelected: (Calendar) -> Unit
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