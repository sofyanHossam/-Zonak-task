package com.example.apis

import androidx.room.TypeConverter
import com.example.apis.data.model.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    private val gson = Gson()
    private val sourceType: Type = object : TypeToken<Source>() {}.type

    @TypeConverter
    fun fromSource(source: Source?): String? {
        return source?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toSource(sourceString: String?): Source? {
        return sourceString?.let { gson.fromJson(it, sourceType) }
    }
}
