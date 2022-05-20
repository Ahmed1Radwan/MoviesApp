package com.ahmedhamdy.myapplication2.model.convertors

import androidx.room.TypeConverter
import com.ahmedhamdy.myapplication2.model.entities.Genre
import com.google.gson.Gson
import java.util.*

class TypeConvertors {

    @TypeConverter
    fun genreListToJson(value: List<Genre>?): String?{
        return if (value == null) null else Gson().toJson(value)
    }

    @TypeConverter
    fun genreJsonToList(value: String?): List<Genre>?{
        return if (value == null) null else Gson().fromJson(value, Array<Genre>::class.java).toList()
    }

}
