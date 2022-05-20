package com.ahmedhamdy.myapplication2.data.remote.paging

import androidx.paging.rxjava3.RxPagingSource
import com.ahmedhamdy.myapplication2.data.repo.MovieRepository
import com.ahmedhamdy.myapplication2.model.entities.Movie

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviePopularPagingSource(
    private val movieRepo: MovieRepository
    )
    : RxPagingSource<Int, Movie>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val page = params.key ?: 1
        return movieRepo.getMoviesServiceInstance()
            .getPopularMoviePage(page)
            .subscribeOn(Schedulers.io())
            .map {
                if (page == 1){
                    movieRepo.savePopularFirstPage(it.movies)
                }
                LoadResult.Page(
                    data = it.movies,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page == it.totalPages) null else page+1
                ) as LoadResult<Int, Movie>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}