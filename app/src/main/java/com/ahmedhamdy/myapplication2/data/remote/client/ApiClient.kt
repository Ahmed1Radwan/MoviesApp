package com.ahmedhamdy.myapplication2.data.remote.client

import com.ahmedhamdy.myapplication2.data.remote.api.MovieService
import com.ahmedhamdy.myapplication2.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {


    private val authRequest: Retrofit by lazy {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(Authentication())
            .build()

        val gson = GsonBuilder().setLenient().create()
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
    }

    fun getAuthRequestRetrofit(): Retrofit = authRequest

}