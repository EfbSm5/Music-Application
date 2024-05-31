package com.example.mymusicapplication

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File

private const val TAG = "PersonalData"

data class UserProfile(
    val name: String = "",
    val sex: String = "",
    val birthDay: String = "",
    val preference: List<String>? = null,
    val useEmotion: Float = 0f,
    val photoFile: File? = null
)

fun toJson(userProfile: UserProfile): String {
    val moshi =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).add(File::class.java, fileAdapter).build()
    val userAdapter = moshi.adapter(UserProfile::class.java)
    val json = userAdapter.toJson(userProfile)
    return json
}

fun fromJson(json: String): UserProfile {
    val moshi =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).add(File::class.java, fileAdapter).build()
    val userAdapter = moshi.adapter(UserProfile::class.java)
    try {
        return userAdapter.fromJson(json) ?: UserProfile()
    } catch (e: Exception) {
        Log.d(TAG, "fromJson: ")
        return UserProfile()
    }
}

val fileAdapter = object : JsonAdapter<File>() {
    @FromJson
    override fun fromJson(reader: JsonReader): File? {
        return if (reader.peek() != JsonReader.Token.NULL) {
            File(reader.nextString())
        } else {
            reader.nextNull<Unit>()
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: File?) {
        value?.let {
            writer.value(it.path)
        } ?: writer.nullValue()
    }
}