package com.example.mymusicapplication.ui.page

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun getBirthDay(): String {

    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selected Date: ${selectedDate.time}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            showDatePicker(context, selectedDate) { newDate ->
                selectedDate = newDate
            }
        }) {
            Text("Select Date")
        }
    }
    return selectedDate.time.toString()
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