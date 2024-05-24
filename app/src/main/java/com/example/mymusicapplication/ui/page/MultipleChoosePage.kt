package com.example.mymusicapplication.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mymusicapplication.Questions

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
