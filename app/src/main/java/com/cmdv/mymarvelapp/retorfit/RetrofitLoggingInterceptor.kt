package com.cmdv.mymarvelapp.retorfit

import com.cmdv.mymarvelapp.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitLoggingInterceptor {
    val interceptor: HttpLoggingInterceptor =
    HttpLoggingInterceptor().also {
        when (BuildConfig.FLAVOR) {
            "DEV" -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
    }
}
