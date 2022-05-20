package com.ahmedhamdy.myapplication2.data.remote.api

import com.ahmedhamdy.myapplication2.model.entities.Movie
import com.ahmedhamdy.myapplication2.model.responses.MovieResponse
import com.ahmedhamdy.myapplication2.model.responses.ReviewResponse
import com.ahmedhamdy.myapplication2.model.responses.TrailerResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    fun getPopularMoviePage(@Query("page") page: Int): Single<MovieResponse>
    @GET("movie/top_rated")
    fun getTopRatedMoviePage(@Query("page") page: Int): Single<MovieResponse>
    @GET("movie/{id}/videos")
    fun getMovieTrailers(@Path("id") id: Long, @Query("page") page: Int): Single<TrailerResponse>
    @GET("movie/{id}/reviews")
    fun getMovieReviews(@Path("id") id: Long, @Query("page") page: Int): Single<ReviewResponse>
    @GET("movie/{id}?append_to_response=videos,reviews")
    fun getMovieDetails(@Path("id") id : Long): Single<Movie>

}