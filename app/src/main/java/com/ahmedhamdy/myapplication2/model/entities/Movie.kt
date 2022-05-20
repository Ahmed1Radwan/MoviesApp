package com.ahmedhamdy.myapplication2.model.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.ahmedhamdy.myapplication2.model.responses.ReviewResponse
import com.ahmedhamdy.myapplication2.model.responses.TrailerResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


@Entity(tableName = "Movies")
data class Movie (

    @PrimaryKey
    @ColumnInfo(name="id")
    @SerializedName("id")
    var id: Long = 0,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String? = null,


    @ColumnInfo(name="posterPath")
    @SerializedName("poster_path")
    var posterPath: String? = null,

    @ColumnInfo(name = "backdropPath")
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var overview: String? = null,

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    var popularity: Double = 0.toDouble(),

    @ColumnInfo(name = "voteAverage")
    @SerializedName("vote_average")
    var voteAverage: Double = 0.toDouble(),

    @ColumnInfo(name = "voteCount")
    @SerializedName("vote_count")
    var voteCount: Int = 0,

    @ColumnInfo(name = "releaseDate")
    @SerializedName("release_date")
    var releaseDate: String? = null,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "is_popular")
    var isPopular: Boolean = false,

    @ColumnInfo(name = "is_topRated")
    var isTopRated: Boolean = false,

    @ColumnInfo(name = "genres")
    @SerializedName("genres")
    var genres: List<Genre>? = null,

    @Ignore
    @SerializedName("videos")
    var trailersResponse: TrailerResponse? = null,

    @Ignore
    @SerializedName("reviews")
    var reviewsResponse: ReviewResponse? = null,

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    var originalLanguage: String? = null): Serializable {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val movie = o as? Movie
        return id == movie?.id &&
                movie.popularity.compareTo(popularity) == 0 &&
                movie.voteAverage.compareTo(voteAverage) == 0 &&
                title == movie.title &&
                posterPath == movie.posterPath &&
                overview == movie.overview &&
                releaseDate == movie.releaseDate
    }

    override fun hashCode(): Int {
        return Objects.hash(id, title, posterPath, overview, popularity, voteAverage, releaseDate)
    }
}