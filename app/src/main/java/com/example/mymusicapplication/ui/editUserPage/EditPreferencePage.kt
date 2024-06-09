package com.example.mymusicapplication.ui.editUserPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicapplication.R

@Preview
@Composable
fun PreviewEditPreference() {
    EditPreference {

    }
}

@Composable
fun EditPreference(
    saveData: (List<String>) -> Unit
) {
    val potentialAnswers = listOf(
        ImageAndName("民谣", R.drawable.minyao),
        ImageAndName("ACG", R.drawable.acg),
        ImageAndName("说唱", R.drawable.shuochang),
        ImageAndName("流行", R.drawable.liuxing),
        ImageAndName("爵士", R.drawable.jueshi),
        ImageAndName("古典", R.drawable.gudian),
        ImageAndName("电子", R.drawable.dianzi)
    )
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
            item { Spacer(modifier = Modifier.height(200.dp)) }
            item { Text(text = "你听音乐的爱好是什么", style = TextStyle(fontSize = 30.sp)) }
            itemsIndexed(potentialAnswers) { _, item ->
                CheckBoxAndText(
                    modifier = Modifier.padding(vertical = 6.dp),
                    item = item.name,
                    isSelected = selectedOptions.contains(item.name),
                    imageId = item.imageId
                ) {
                    if (selectedOptions.contains(item.name)) {
                        selectedOptions.remove(item.name)
                    } else {
                        selectedOptions.add(item.name)
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

class ImageAndName(val name: String, val imageId: Int)

