package com.ahmedhamdy.myapplication2.data.repo

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.ahmedhamdy.myapplication2.App
import com.ahmedhamdy.myapplication2.data.remote.paging.MoviePopularPagingSource
import com.ahmedhamdy.myapplication2.data.remote.paging.MovieReviewPagingSource
import com.ahmedhamdy.myapplication2.data.remote.paging.MovieTopRatedPagingSource
import com.ahmedhamdy.myapplication2.data.remote.paging.MovieTrailerPagingSource
import com.ahmedhamdy.myapplication2.data.repo.base.BaseRepository
import com.ahmedhamdy.myapplication2.model.entities.Movie
import com.ahmedhamdy.myapplication2.model.entities.Review
import com.ahmedhamdy.myapplication2.model.entities.Trailer
import com.ahmedhamdy.myapplication2.utils.Constants
import com.ahmedhamdy.myapplication2.workers.DeleteWorker
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private const val TAG = "MovieRepository"
class MovieRepository: BaseRepository() {

    private fun assignBackgroundDeleteWorker(context: Context) {
        val deleteWorkRequest: WorkRequest = PeriodicWorkRequestBuilder<DeleteWorker>(1,
            TimeUnit.HOURS).build()
        WorkManager.getInstance(context).enqueue(deleteWorkRequest)
    }

    fun getPopularMoviesPaging(repository: MovieRepository): Flowable<PagingData<Movie>>?{
        return if(isNetworkAvailable()){
            WorkManager.getInstance(App.INSTANCE).pruneWork()
            repository.assignBackgroundDeleteWorker(App.INSTANCE)

            val popularMoviePagingSource = MoviePopularPagingSource(repository)
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    prefetchDistance = 20,
                    enablePlaceholders = false,
                    initialLoadSize = 20,
                    maxSize = 20 * Constants.TOTALPAGES
                ),
                pagingSourceFactory = {popularMoviePagingSource!!}
            ).flowable
        }else{
            getLocalPopularMovies()
        }
    }
    private fun getLocalPopularMovies(): Flowable<PagingData<Movie>>?{
        return moviesDbDao.getPopularMoviesFirstPage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Constants.firstMovieToShow.postValue(it[0])
                PagingData.from(it)
            }
    }

    fun getTopRatedMoviesPaging(repository: MovieRepository): Flowable<PagingData<Movie>>?{
        return if (isNetworkAvailable()){
            val topRatedMoviePagingSource = MovieTopRatedPagingSource(repository)
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    prefetchDistance = 20,
                    enablePlaceholders = false,
                    initialLoadSize = 20,
                    maxSize = 20 * Constants.TOTALPAGES
                ),
                pagingSourceFactory = {topRatedMoviePagingSource!!}
            ).flowable
        }else{
            getLocalTopRatedMovies()
        }

    }

    private fun getLocalTopRatedMovies(): Flowable<PagingData<Movie>>?{
        return moviesDbDao.getTopRatedMoviesFirstPage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                PagingData.from(it)
            }
    }

    fun savePopularFirstPage(movies: List<Movie>){
        movies.forEach {
            it.isPopular = true
            getMoviesDetails(it)
        }
        Constants.firstMovieToShow.postValue(movies[0])
    }

    fun saveTopRatedFirstPage(movies: List<Movie>){
        movies.forEach {
            it.isTopRated = true
            getMoviesDetails(it)
        }
    }

    private fun getMoviesDetails(movie: Movie){
        compositeDisposable.add(
            getMoviesServiceInstance().getMovieDetails(movie.id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                    {
                        movie.genres = it.genres
                        compositeDisposable.add(
                            moviesDbDao.insertMovieDetails(movie)
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe()
                        )
                        it.backdropPath?.let { it1 -> saveImage(true, it1, null) }
                        it.reviewsResponse?.let { it1 -> saveMovieReviews(it1.reviews, movie.id) }
                        it.trailersResponse?.let {
                                it1 -> saveMovieTrailers(it1.trailers,movie.id)
                                saveImage(false, "", it1.trailers)
                        }
                    },
                    {
                        Log.d(TAG, "addGenreOfEachMovie: Error getting genres")
                    }
                )
        )
    }


    fun getMovieTrailersPaging(repository: MovieRepository, movieId: Long): Flowable<PagingData<Trailer>>?{
        return if(isNetworkAvailable()){
                val trailerPagingSource = MovieTrailerPagingSource(repository, movieId = movieId)
                return Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        prefetchDistance = 10,
                        enablePlaceholders = true,
                        initialLoadSize = 10
                    ),
                    pagingSourceFactory = {trailerPagingSource!!}
                ).flowable
            }else{
                getLocalMovieTrailers(movieId)
            }

    }
    private fun getLocalMovieTrailers(id: Long): Flowable<PagingData<Trailer>>?{
        return moviesDbDao.getMovieTrailers(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                PagingData.from(it)
            }
    }

    fun getMovieReviewsPaging(repository: MovieRepository, movieId: Long): Flowable<PagingData<Review>>?{
        return if (isNetworkAvailable()){
            val reviewPagingSource = MovieReviewPagingSource(repository,movieId = movieId)
            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    prefetchDistance = 10,
                    enablePlaceholders = false,
                    initialLoadSize = 10
                ),
                pagingSourceFactory = {reviewPagingSource!!}
            ).flowable
        }else{
            getLocalMovieReviews(movieId)
        }

    }
    private fun getLocalMovieReviews(id: Long): Flowable<PagingData<Review>>?{
        return moviesDbDao.getMovieReviews(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                PagingData.from(it)
            }
    }


    fun getLocalFavoriteMovies(): Flowable<List<Movie>>?{
        return moviesDbDao.getAllFavoriteMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }



    /*
    CodeReview I noticed that the data fetched from the local data source is different from the data fetched from the network
            the local data should be updated everytime you fetch it from the network
     */
    /*
        I save the first page of popular and topRated every time user open the app and there is a network, if there is no network
        data get from local so the order of movies is changed to be like what is saved into room database
     */

    /*
    CodeReview caching logic doesn't work on tablet
     */
    /*
        I test it after changes in the emulator pixel C making emulator in airplane mode and it works
     */
    /*
    CodeReview all the movie's details (including poster, rating, language, reviews list and trailers list) should be cached too
        you can use glide to cache poster image, and if the user clicked a trailer show a toast to display that this action needs internet
     */
    /*
        I added the details of first page of both popular and topRated movies, I was thinking that only saving movies details
        for movies that user clicking and also added the toast for trailers when there is no network
     */



}