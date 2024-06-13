package com.example.mymusicapplication


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import com.example.mymusicapplication.database.AppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File


class EditUserProfileViewModel : ViewModel() {
    private val _profile = MutableStateFlow(UserProfile())
    val profile: StateFlow<UserProfile> = _profile.asStateFlow()

    fun updateName(name: String) {
        _profile.value = _profile.value.copy(name = name)
    }

    fun updateSex(sex: String) {
        _profile.value = _profile.value.copy(sex = sex)
    }

    fun updateBirthday(birthday: String) {
        _profile.value = _profile.value.copy(birthDay = birthday)
    }

    fun updatePreferences(preferences: List<String>) {
        _profile.value = _profile.value.copy(preference = preferences)
    }

    fun updateEmotion(emotion: Float) {
        _profile.value = _profile.value.copy(useEmotion = emotion)
    }

    fun updateAvatar(avatar: File) {
        _profile.value = _profile.value.copy(photoFile = avatar)
    }

    @Composable
    fun InsertDataToDataBase(context: Context) {
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            coroutineScope.launch(Dispatchers.IO) {
                AppDataBase.getDatabase(context).userDao().insert(
                    UserProfile(
                        id = AppDataBase.getDatabase(context).userDao().getCount() + 1,
                        name = _profile.value.name,
                        sex = _profile.value.sex,
                        birthDay = _profile.value.birthDay,
                        preference = _profile.value.preference,
                        useEmotion = _profile.value.useEmotion,
                        photoFile = _profile.value.photoFile,
                    )
                )
            }
        }
    }
}

