package com.example.mymusicapplication

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Calendar

class PersonalData : AppCompatActivity() {
    private val sexQuestions =
        Questions("你的性别", listOf("男", "女", "其他"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val name = editName()
        }
    }

    @Composable
    fun singleChoose(questions: Questions): String? {
        val selectedOption = remember { mutableStateOf<String?>(null) } // 存储当前选中的选项
        val options = questions.getAnswer()
        Column(
            modifier = Modifier.padding(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = questions.getQuestion())
            options.forEach { option ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    RadioButton(
                        selected = selectedOption.value == option, // 如果当前选项是选中的选项，则RadioButton被选中
                        onClick = { selectedOption.value = option },
                    )
                }
            }
            Button(onClick = {
                setContent { getBirthDay() }
            }) {
                Text(text = "下一题")
            }
        }
        return selectedOption.value
    }

    @Composable
    fun MultipleChoices(questions: Questions) {
        val selectedOptions = remember { mutableStateListOf<String>() }
        val options = questions.getAnswer()
        Column(
            modifier = Modifier.padding(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = questions.getQuestion())
            options.forEach { option ->
                val isSelected = remember(option) { mutableStateOf(false) }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Checkbox(
                        checked = isSelected.value,
                        onCheckedChange = { isSelected.value = it },
                        modifier = Modifier.toggleable(
                            value = isSelected.value,
                            onValueChange = {
                                isSelected.value = !isSelected.value
                                if (isSelected.value) {
                                    selectedOptions.add(option)
                                } else {
                                    selectedOptions.remove(option)
                                }
                            }
                        ),
                    )
                }
            }
        }
    }

    @Composable
    fun editName(): String {
        val name = remember { mutableStateOf("") }
        Column(
            modifier = Modifier.padding(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "请输入你的名字")
            TextField(
                value = name.value,
                onValueChange = { newText -> name.value = newText },
                label = { Text("请输入姓名") })
            Button(onClick = {
                setContent {
                    val sex = singleChoose(sexQuestions)
                }
            }) {
                Text(text = "下一题")
            }
        }
        return name.value
    }

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
}