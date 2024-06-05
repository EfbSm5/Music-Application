package com.example.mymusicapplication.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mymusicapplication.EditUserProfileViewModel
import com.example.mymusicapplication.QuestionsAndAnswers
import com.example.mymusicapplication.lastScreen
import com.example.mymusicapplication.nextScreen

@Composable
fun EditPreference(
    questionsAndAnswers: QuestionsAndAnswers,
    viewModel: EditUserProfileViewModel,
    navController: NavController
) {
    val selectedOptions = remember { mutableStateListOf<String>() }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Text(text = questionsAndAnswers.getQuestion()) }
        itemsIndexed(questionsAndAnswers.getPotentialAnswer()) { _, item ->
            val isSelected = remember { mutableStateOf(false) }
            Row {
                Text(
                    text = item, modifier = Modifier.padding(start = 8.dp)
                )
                Checkbox(
                    checked = isSelected.value,
                    onCheckedChange = {
                        isSelected.value = !isSelected.value
                        if (isSelected.value) {
                            selectedOptions.add(item)
                        } else {
                            selectedOptions.remove(item)
                        }
                    },
                )
            }
        }
        item {
            Row {
                Button(onClick = {
                    viewModel.updatePreferences(selectedOptions)
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
