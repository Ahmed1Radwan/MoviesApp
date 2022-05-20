package com.ahmedhamdy.myapplication2.model.responses

import com.ahmedhamdy.myapplication2.model.entities.Trailer
import com.google.gson.annotations.SerializedName

data class TrailerResponse(
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("total_results")
    var totalResults: Int = 0,
    @SerializedName("total_pages")
    var totalPages: Int = 0,
    @SerializedName("results")
    var trailers: List<Trailer>
)