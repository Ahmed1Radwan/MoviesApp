package com.ahmedhamdy.myapplication2.data.remote.client

import com.ahmedhamdy.myapplication2.utils.Constants.api_key
import okhttp3.Interceptor
import okhttp3.Response

class Authentication : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var url = request.url().newBuilder()
            .addQueryParameter("api_key", api_key)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}