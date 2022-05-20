package com.ahmedhamdy.myapplication2.utils

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.ahmedhamdy.myapplication2.model.entities.Movie

object Constants {

    val firstMovieToShow: MutableLiveData<Movie> = MutableLiveData()

    const val BASE_URL: String = "https://api.themoviedb.org/3/"
    const val api_key: String = "6675b80b92722b89ee281264add1a4e6"
    const val BACKDROPIMAGURL: String = "https://image.tmdb.org/t/p/w780"
    const val IMAGURL: String = "https://image.tmdb.org/t/p/w185"
    const val TOTALPAGES: Int = 500
    const val YOUTUBE_APIKEY = "AIzaSyBRDst3movqd31KAKy4CdIn6wk0O8uWJi8"
    const val MOVIEObject_EXTRA = "movie_object"
    const val MOVIEID_EXTRA = "movie_id"
    const val SHAREDPREFKEY = "favorites"
    const val SHAREDPREFNAME = "favoriteMoviesIds"

    const val MOVIE_OVERVIEW = "movie_overview"
    const val MOVIE_VOTECOUNT = "movie_vote_count"
    const val MOVIE_VOTEAVERG = "movie_vote_avg"
    const val MOVIE_LANGUAGE = "movie_language"
    const val MOVIE_RELEASE = "movie_release_date"


}