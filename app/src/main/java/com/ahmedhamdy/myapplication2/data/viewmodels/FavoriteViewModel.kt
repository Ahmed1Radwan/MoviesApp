package com.ahmedhamdy.myapplication2.data.viewmodels

import androidx.lifecycle.ViewModel
import com.ahmedhamdy.myapplication2.data.repo.MovieRepository
import com.ahmedhamdy.myapplication2.model.entities.Movie
import io.reactivex.rxjava3.core.Flowable

class FavoriteViewModel(private val repository: MovieRepository = MovieRepository()): ViewModel() {

    private var flowableFavoriteMovies: Flowable<List<Movie>>?= null

    fun getFavoriteMovies(){
        flowableFavoriteMovies = repository.getLocalFavoriteMovies()
    }
    fun getFavoriteFlowable(): Flowable<List<Movie>>?{
        return flowableFavoriteMovies
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearDisposables()
    }
}