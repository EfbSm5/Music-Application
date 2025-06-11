package com.example.mymusicapplication.database

import androidx.room.TypeConverter
import java.io.File

class Converters {

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toList(concatenatedString: String?): List<String>? {
        return concatenatedString?.split(",")
    }

    @TypeConverter
    fun fromFilePath(file: File?): String? {
        return file?.absolutePath
    }

    @TypeConverter
    fun toFilePath(filePath: String?): File? {
        return filePath?.let { File(it) }
    }
}