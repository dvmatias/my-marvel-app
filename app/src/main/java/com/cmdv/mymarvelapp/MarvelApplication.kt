package com.cmdv.mymarvelapp

import android.app.Application
import com.cmdv.mymarvelapp.koin_modules.navigationModule
import com.cmdv.mymarvelapp.koin_modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
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
                    viewModelModule
                )
            )
        }
    }

}