package com.ahmedhamdy.myapplication2.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "TrailersTable")
data class Trailer(

    @PrimaryKey
    @SerializedName("id")
    var id: String,


    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0,

    @ColumnInfo(name = "key")
    @SerializedName("key")
    var key: String? = null,

    @SerializedName("site")
    var site: String? = null,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var title: String? = null

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Trailer

        if (id != other.id) return false
        if (key != other.key) return false
        if (site != other.site) return false
        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (key?.hashCode() ?: 0)
        result = 31 * result + (site?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        return result
    }
}