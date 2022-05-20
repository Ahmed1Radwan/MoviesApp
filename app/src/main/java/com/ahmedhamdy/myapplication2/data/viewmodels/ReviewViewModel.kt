package com.ahmedhamdy.myapplication2.data.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ahmedhamdy.myapplication2.data.repo.MovieRepository
import com.ahmedhamdy.myapplication2.model.entities.Review
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.cancel

class ReviewViewModel(private val repository: MovieRepository = MovieRepository()): ViewModel() {
    private var pagingReviewDataFlow: Flowable<PagingData<Review>>?= null
    private val coroutineScope = this.viewModelScope

    fun getMovieReviewsPaging(movieId: Long){
        pagingReviewDataFlow = repository.getMovieReviewsPaging(repository, movieId)
        pagingReviewDataFlow!!.cachedIn(coroutineScope)

    }

    fun getReviewPagingDataFlow(): Flowable<PagingData<Review>>? {
        return pagingReviewDataFlow
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
        repository.clearDisposables()
    }
}