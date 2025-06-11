package com.example.mymusicapplication.ui.editUserPage

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicapplication.EditUserProfileViewModel
import com.example.mymusicapplication.R
import com.example.mymusicapplication.UserProfile


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditPreference(
    viewModel: EditUserProfileViewModel
) {
    val potentialAnswers = listOf(
        Pair("民谣", R.drawable.minyao),
        Pair("ACG", R.drawable.acg),
        Pair("说唱", R.drawable.shuochang),
        Pair("流行", R.drawable.liuxing),
        Pair("爵士", R.drawable.jueshi),
        Pair("古典", R.drawable.gudian),
        Pair("电子", R.drawable.dianzi)
    )
    val profile by viewModel.profile.collectAsState()
    EditPreferenceScreen(potentialAnswers = potentialAnswers, profile = profile) { item ->
        val preference = profile.preference.toMutableList()
        if (profile.preference.contains(item)) {
            preference.remove(item)
        } else {
            preference.add(item)
        }
        viewModel.updatePreferences(preference)
    }

}

@Composable
private fun EditPreferenceScreen(
    potentialAnswers: List<Pair<String, Int>>, profile: UserProfile, onClick: (String) -> Unit
) {
    Surface(Modifier.padding(20.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "你听音乐的爱好是什么",
                style = TextStyle(fontSize = 30.sp),
                color = MaterialTheme.colorScheme.outline
            )
            for (item in potentialAnswers) {
                CheckBoxAndText(
                    modifier = Modifier.padding(vertical = 6.dp),
                    item = item.first,
                    isSelected = profile.preference.contains(item.first),
                    imageId = item.second
                ) {
                    onClick(item.first)
                }
            }
        }
    }
}

@Composable
private fun CheckBoxAndText(
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
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
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

