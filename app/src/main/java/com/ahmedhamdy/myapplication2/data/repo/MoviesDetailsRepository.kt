package com.ahmedhamdy.myapplication2.data.repo


import com.ahmedhamdy.myapplication2.data.repo.base.BaseRepository
import com.ahmedhamdy.myapplication2.model.entities.Movie
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

private const val TAG = "MoviesDetailsRepository"
class MoviesDetailsRepository : BaseRepository(){

    fun getMovieDetails(movieId: Long): Single<Movie>?{
        return if(isNetworkAvailable()){
            getMoviesServiceInstance()
                .getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }else{
            return moviesDbDao.getMovieDetail(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun insertMovieToFavoriteDatabase(currentMovie: Movie?) {

        currentMovie?.let {
            currentMovie.isFavorite = true
            compositeDisposable.add(
                moviesDbDao.insertMovieDetails(currentMovie)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe()
            )
        }
    }
    fun deleteMovieFromFavoriteDatabase(id: Long) {
        compositeDisposable.add(
            moviesDbDao.deleteMovieFromFavorites(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe()
        )
    }

}