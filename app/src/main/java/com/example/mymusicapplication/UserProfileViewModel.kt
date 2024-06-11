package com.example.mymusicapplication


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File


class EditUserProfileViewModel : ViewModel() {
    val profile = MutableStateFlow(UserProfile())

    fun updateName(name: String) {
        profile.value = profile.value.copy(name = name)
    }

    fun updateSex(sex: String) {
        profile.value = profile.value.copy(sex = sex)
    }

    fun updateBirthday(birthday: String) {
        profile.value = profile.value.copy(birthDay = birthday)
    }

    fun updatePreferences(preferences: List<String>) {
        profile.value = profile.value.copy(preference = preferences)
    }

    fun updateEmotion(emotion: Float) {
        profile.value = profile.value.copy(useEmotion = emotion)
    }

    fun updateAvatar(avatar: File) {
        profile.value = profile.value.copy(photoFile = avatar)
    }
}

