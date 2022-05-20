package com.ahmedhamdy.myapplication2.data.remote.paging

import androidx.paging.rxjava3.RxPagingSource
import com.ahmedhamdy.myapplication2.data.repo.MovieRepository
import com.ahmedhamdy.myapplication2.data.repo.MoviesDetailsRepository
import com.ahmedhamdy.myapplication2.model.entities.Trailer

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieTrailerPagingSource(
    private val movieRepo: MovieRepository,
    var movieId: Long
    )
    : RxPagingSource<Int, Trailer>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Trailer>> {
        val page = params.key ?: 1

        return movieRepo.getMoviesServiceInstance()
            .getMovieTrailers(movieId, page)
            .subscribeOn(Schedulers.io())
            .map {
                if (page == 1){
                    movieRepo.saveMovieTrailers(it.trailers, movieId)
                }
                LoadResult.Page(
                    data = it.trailers,
                    prevKey = if(it.totalPages == 0 || page == 1) null else page - 1,
                    nextKey = if(it.totalPages == 0 || page == it.totalPages) null else page+1
                ) as LoadResult<Int, Trailer>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}