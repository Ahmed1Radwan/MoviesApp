package com.ahmedhamdy.myapplication2.data.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ahmedhamdy.myapplication2.data.repo.MovieRepository
import com.ahmedhamdy.myapplication2.model.entities.Movie
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.cancel

class TopRatedViewModel(private val repository: MovieRepository = MovieRepository()) : ViewModel() {
    private var pagingTopRatedDataFlow : Flowable<PagingData<Movie>>? = null
    private val coroutineScope = this.viewModelScope


    fun getTopRatedMoviesPaging(){
        pagingTopRatedDataFlow = repository.getTopRatedMoviesPaging(repository)
        pagingTopRatedDataFlow!!.cachedIn(coroutineScope)
    }

    fun getTopRatedPagingDaFlow(): Flowable<PagingData<Movie>>? {
        return pagingTopRatedDataFlow
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
        repository.clearDisposables()
    }

}