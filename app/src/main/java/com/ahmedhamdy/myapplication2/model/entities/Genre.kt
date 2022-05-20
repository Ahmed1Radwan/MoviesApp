package com.ahmedhamdy.myapplication2.model.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Genre(

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var name: String? = null
): Serializable