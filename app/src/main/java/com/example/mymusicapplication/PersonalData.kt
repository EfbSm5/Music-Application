package com.example.mymusicapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class PersonalData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val questions =
            Questions("你的性别", listOf("男", "女", "其他"))
        setContent {
            SingleChoose(questions)
        }

    }

    @Composable
    fun SingleChoose(questions: Questions) {
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
            Button(onClick = {}) {

            }
        }
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


}