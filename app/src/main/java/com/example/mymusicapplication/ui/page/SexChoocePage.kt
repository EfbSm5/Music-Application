package com.example.mymusicapplication.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mymusicapplication.QuestionsAndAnswers

@Composable
fun SexChoose(
    questionsAndAnswers: QuestionsAndAnswers,
    onSexConfirmed: (String) -> Unit,
    onNavigateToNextScreen: () -> Unit = {}
) {
    var selectedOption by remember { mutableStateOf<String>("") } // 存储当前选中的选项
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Text(text = questionsAndAnswers.getQuestion()) }
        itemsIndexed(questionsAndAnswers.getPotentialAnswer()) { _, item ->
            LazyRow(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                item {
                    Text(
                        text = item, modifier = Modifier.padding(start = 8.dp)
                    )
                }
                item {
                    RadioButton(
                        selected = selectedOption == item, // 如果当前选项是选中的选项，则RadioButton被选中
                        onClick = { selectedOption = item },
                    )
                }
            }
        }
        item {
            Button(onClick = {
                if (selectedOption.isNotEmpty()) {
                    onSexConfirmed(selectedOption)
                    onNavigateToNextScreen()
                }
            }) {
                Text(text = "下一题")
            }

        }
    }


}
