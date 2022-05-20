package com.ahmedhamdy.myapplication2.data.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import androidx.work.WorkManager
import com.ahmedhamdy.myapplication2.data.repo.MovieRepository
import com.ahmedhamdy.myapplication2.model.entities.Movie
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.cancel

private const val TAG = "PopularViewModel"
class PopularViewModel(private val repository: MovieRepository = MovieRepository()) : ViewModel() {
    private var pagingPopularDataFlow: Flowable<PagingData<Movie>>? = null
    private val coroutineScope = this.viewModelScope

    fun getPopularMoviesPaging(){
        pagingPopularDataFlow = repository.getPopularMoviesPaging(repository)
        pagingPopularDataFlow!!.cachedIn(coroutineScope)
    }

    fun getPopularPagingDataFlow(): Flowable<PagingData<Movie>>? {
        return pagingPopularDataFlow
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
        repository.clearDisposables()
    }

}