package com.cmdv.mymarvelapp.retorfit

import com.cmdv.mymarvelapp.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

private const val GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

class RetrofitManager {

    fun getInstance(): Retrofit {
        val gson = GsonBuilder()
            .setDateFormat(GSON_DATE_FORMAT)
            .create()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(RetrofitClient.client)
            .build()
    }
}