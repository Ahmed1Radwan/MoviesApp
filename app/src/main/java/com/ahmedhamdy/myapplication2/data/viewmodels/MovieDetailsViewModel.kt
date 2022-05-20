package com.ahmedhamdy.myapplication2.data.viewmodels

import androidx.lifecycle.ViewModel
import com.ahmedhamdy.myapplication2.data.repo.MoviesDetailsRepository
import com.ahmedhamdy.myapplication2.model.entities.Movie
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable

private const val TAG = "MovieDetailsViewModel"
class MovieDetailsViewModel(
    private val repository: MoviesDetailsRepository = MoviesDetailsRepository()
    )
    : ViewModel() {

    // CodeReview it will be better if you can map movies object to movieDetails or vise versa and use a the same Single for both of them if possible
    private var singleMovieDetailObservable: Single<Movie>? = null
    private val composite = CompositeDisposable()

    fun getMovieDetails(movieId: Long){
        /* CodeReview the repo should be responsible for fetching data from which remote or local data source not the viewmodel
        */
        singleMovieDetailObservable = repository.getMovieDetails(movieId)
    }

    fun getMovieDetailsObservable(): Single<Movie>?{
        return singleMovieDetailObservable
    }


    fun deleteMovieFromFavoriteDatabase(id: Long) {
        repository.deleteMovieFromFavoriteDatabase(id)
    }

    fun insertMovieToFavoriteDatabase(currentMovie: Movie) {
        repository.insertMovieToFavoriteDatabase(currentMovie)
    }



    fun getNetworkState(): Boolean = repository.isNetworkAvailable()

    override fun onCleared() {
        super.onCleared()
        repository.clearDisposables()
        composite.clear()
    }
}