package com.example.mymusicapplication


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File


class EditUserProfileViewModel : ViewModel() {
    private val _profile = MutableStateFlow(UserProfile())
    val profile: StateFlow<UserProfile> = _profile

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
}

