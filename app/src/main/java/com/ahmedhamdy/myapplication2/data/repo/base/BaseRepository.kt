package com.ahmedhamdy.myapplication2.data.repo.base

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.ahmedhamdy.myapplication2.App
import com.ahmedhamdy.myapplication2.R
import com.ahmedhamdy.myapplication2.data.local.api.Dao
import com.ahmedhamdy.myapplication2.data.remote.api.MovieService
import com.ahmedhamdy.myapplication2.data.remote.client.ApiClient
import com.ahmedhamdy.myapplication2.db
import com.ahmedhamdy.myapplication2.model.entities.Review
import com.ahmedhamdy.myapplication2.model.entities.Trailer
import com.ahmedhamdy.myapplication2.utils.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

private const val TAG = "BaseRepository"
abstract class BaseRepository {

    private val moviesServiceInterface = ApiClient.getAuthRequestRetrofit().create(MovieService::class.java)
    fun getMoviesServiceInstance(): MovieService = moviesServiceInterface

    protected val moviesDbDao: Dao = db.movieDao()
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()


    fun isNetworkAvailable(): Boolean{
        val connectivityManager = App.INSTANCE.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected;
    }

    fun saveImage(isBackDrop: Boolean, imagePath: String, trailers: List<Trailer>?){
        if(isBackDrop){
            Glide.with(App.INSTANCE)
                .load(Constants.BACKDROPIMAGURL + imagePath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.md_grey_200)
                .submit()
        }else{
            // trailer Image
            trailers?.forEach {
                val thumbnail = "https://img.youtube.com/vi/${it.key}/hqdefault.jpg"
                Glide.with(App.INSTANCE)
                    .load(thumbnail)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.color.md_grey_200)
                    .submit()
            }
        }

    }

    fun saveMovieReviews(reviews: List<Review>, id: Long){
        reviews.forEach {
            it.moveId = id
        }
        moviesDbDao.insertMovieReviews(reviews)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {
                    compositeDisposable.add(d)
                }
                override fun onComplete() {}
                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: " + e?.message)
                }

            })
    }

    fun saveMovieTrailers(trailers: List<Trailer>, id: Long){
        trailers.forEach {
            it.movieId = id
        }
        moviesDbDao.insertMovieTrailers(trailers)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {
                    compositeDisposable.add(d)
                }
                override fun onComplete() {}
                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: " + e?.message)
                }
            })
    }

    fun clearDisposables(){
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }
}