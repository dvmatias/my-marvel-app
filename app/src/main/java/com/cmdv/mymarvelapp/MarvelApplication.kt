package com.cmdv.mymarvelapp

import android.app.Application
import com.cmdv.mymarvelapp.koin_modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarvelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MarvelApplication)
            modules(
                listOf(
                    navigationModule,
                    viewModelModule,
                    useCaseModule,
                    repositoryModule,
                    serviceModule,
                    networkModule,
                    roomModule,
                    adapterModule
                )
            )
        }
    }

}