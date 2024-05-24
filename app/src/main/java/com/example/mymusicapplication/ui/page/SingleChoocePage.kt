package com.example.mymusicapplication.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mymusicapplication.Questions

@Composable
fun SingleChoose(questions: Questions, sex: MutableList<String>) {
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
            if (selectedOption.value != null) {
                sex.add(selectedOption.value!!)
            }
        }) {
            Text(text = "下一题")
        }
    }
}