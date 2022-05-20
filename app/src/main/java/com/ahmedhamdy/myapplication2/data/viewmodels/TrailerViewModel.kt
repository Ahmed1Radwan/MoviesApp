package com.ahmedhamdy.myapplication2.data.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ahmedhamdy.myapplication2.data.repo.MovieRepository
import com.ahmedhamdy.myapplication2.model.entities.Trailer
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.cancel


class TrailerViewModel(private val repository: MovieRepository = MovieRepository()): ViewModel() {

    private var pagingTrailersDataFlowable: Flowable<PagingData<Trailer>>?= null
    private val coroutineScope = this.viewModelScope

    fun getMovieTrailersPaging(movieId: Long){
        pagingTrailersDataFlowable = repository.getMovieTrailersPaging(repository, movieId)
        pagingTrailersDataFlowable!!.cachedIn(coroutineScope)
    }


    fun getTrailersPagingDataFlowable(): Flowable<PagingData<Trailer>>? {
        return pagingTrailersDataFlowable
    }


    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
        repository.clearDisposables()
    }

}