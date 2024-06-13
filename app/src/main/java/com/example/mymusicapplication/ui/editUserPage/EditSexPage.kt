package com.example.mymusicapplication.ui.editUserPage

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicapplication.EditUserProfileViewModel
import com.example.mymusicapplication.QuestionsAndAnswers
import com.example.mymusicapplication.UserProfile


@Composable
fun EditSex(
    questionsAndAnswers: QuestionsAndAnswers, viewModel: EditUserProfileViewModel
) {
    val profile by viewModel.profile.collectAsState()
    EditSexScreen(viewModel, profile, questionsAndAnswers)
}

@Composable
private fun EditSexScreen(
    viewModel: EditUserProfileViewModel,
    profile: UserProfile,
    questionsAndAnswers: QuestionsAndAnswers
) {
    Surface {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = questionsAndAnswers.getQuestion(),
                    style = TextStyle(fontSize = 30.sp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            item { Spacer(modifier = Modifier.height(200.dp)) }
            itemsIndexed(questionsAndAnswers.getPotentialAnswer()) { _, item ->
                OptionsAndChoice(item = item, selectedOption = profile.sex) {
                    viewModel.updateSex(item)
                }
            }
        }
    }
}

@Composable
private fun OptionsAndChoice(item: String, selectedOption: String, onClick: () -> Unit) {
    Surface(
        color = if (item != selectedOption) MaterialTheme.colorScheme.surface
        else MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier
            .padding(10.dp)
            .clip(shape = MaterialTheme.shapes.large)
    ) {
        Row(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.large
                )
                .clickable {
                    onClick()
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item, modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
            RadioButton(
                selected = selectedOption == item, // 如果当前选项是选中的选项，则RadioButton被选中
                onClick = { onClick() },
            )
        }
    }
}
