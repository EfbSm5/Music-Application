package com.example.mymusicapplication.ui.editUserPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusicapplication.QuestionsAndAnswers
import com.example.mymusicapplication.R


@Composable
fun EditPreference(
    questionsAndAnswers: QuestionsAndAnswers, saveData: (List<String>) -> Unit
) {
    val selectedOptions = remember { mutableStateListOf<String>() }
    DisposableEffect(key1 = Unit) {
        onDispose {
            saveData(selectedOptions)
        }
    }
    Surface {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Text(text = questionsAndAnswers.getQuestion()) }
            itemsIndexed(questionsAndAnswers.getPotentialAnswer()) { _, item ->
                CheckBoxAndText(
                    modifier = Modifier.padding(vertical = 6.dp),
                    item = item,
                    isSelected = selectedOptions.contains(item),
                    imageId = R.drawable.ic_launcher_foreground
                ) {
                    if (selectedOptions.contains(item)) {
                        selectedOptions.remove(item)
                    } else {
                        selectedOptions.add(item)
                    }
                }
            }
        }
    }
}

@Composable
fun CheckBoxAndText(
    modifier: Modifier = Modifier,
    item: String,
    isSelected: Boolean,
    imageId: Int,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        modifier = modifier.clip(MaterialTheme.shapes.large)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
                .border(
                    width = 1.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.large
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = item
            )
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = isSelected,
                onCheckedChange = {
                    onClick()
                },
            )
        }
    }
}

@Preview
@Composable
private fun CheckboxPreview() {
    var isChecked by remember { mutableStateOf(false) }
    CheckBoxAndText(
        item = "Preview", isSelected = isChecked, imageId = R.drawable.minyao
    ) {
        isChecked = !isChecked
    }
}

class ImageAndName(val name: String, val imageId: Int) {
    public val minyao: ImageAndName = ImageAndName("民谣", R.drawable.minyao)
}