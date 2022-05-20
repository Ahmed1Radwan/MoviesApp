package com.ahmedhamdy.myapplication2.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ReviewsTable")
data class Review (
    @PrimaryKey
    @SerializedName("id")
    var reviewId: String,

    @ColumnInfo(name = "movieId")
    var moveId: Long = 0,

    @ColumnInfo(name = "author")
    @SerializedName("author")
    var author: String? = null,

    @ColumnInfo(name = "content")
    @SerializedName("content")
    var content: String? = null,

    @SerializedName("url")
    var reviewUrl: String? = null


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Review

        if (reviewId != other.reviewId) return false
        if (author != other.author) return false
        if (content != other.content) return false
        if (reviewUrl != other.reviewUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = reviewId.hashCode()
        result = 31 * result + (author?.hashCode() ?: 0)
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + (reviewUrl?.hashCode() ?: 0)
        return result
    }
}