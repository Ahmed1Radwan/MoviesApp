package com.ahmedhamdy.myapplication2.data.local.api

import androidx.room.*
import androidx.room.Dao
import com.ahmedhamdy.myapplication2.model.entities.Genre
import com.ahmedhamdy.myapplication2.model.entities.Movie
import com.ahmedhamdy.myapplication2.model.entities.Review
import com.ahmedhamdy.myapplication2.model.entities.Trailer
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(movies: Movie): Completable

    @Query("select * from Movies where id = :movieId")
    fun getMovieDetail(movieId: Long): Single<Movie>

    @Query("select * from Movies where is_favorite = 1")
    fun getAllFavoriteMovies(): Flowable<List<Movie>>

    @Query("select * from Movies where is_popular = 1")
    fun getPopularMoviesFirstPage(): Flowable<List<Movie>>

    @Query("select * from Movies where  is_topRated = 1")
    fun getTopRatedMoviesFirstPage(): Flowable<List<Movie>>

    @Query("delete from Movies where id = :movieId")
    fun deleteMovieFromFavorites(movieId: Long): Completable

    @Query("delete from Movies where is_favorite = 0")
    fun deleteAllExceptFavorite(): Completable

    @Query("select count() from Movies where is_popular = 1")
    fun checkIfPopularFirstPageSaved(): Single<Int>

    @Query("select count() from Movies where is_topRated = 1")
    fun checkIfTopRatedFirstPageSaved(): Single<Int>

    @Insert(entity = Review::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieReviews(reviews: List<Review>): Completable

    @Query("select * from ReviewsTable where movieId= :movieId")
    fun getMovieReviews(movieId: Long): Flowable<List<Review>>

    @Query("delete from ReviewsTable")
    fun deleteAllReviews(): Completable

    @Insert(entity = Trailer::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieTrailers(trailer: List<Trailer>):Completable

    @Query("select * from TrailersTable where movie_id = :id")
    fun getMovieTrailers(id: Long): Flowable<List<Trailer>>

    @Query("delete from TrailersTable")
    fun deleteAllTrailers(): Completable

}
