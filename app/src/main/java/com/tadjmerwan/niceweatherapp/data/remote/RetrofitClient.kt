package com.tadjmerwan.niceweatherapp.data.remote

import com.tadjmerwan.niceweatherapp.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {
    fun get(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .readTimeout(Config.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(Config.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(Config.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit
    }
}