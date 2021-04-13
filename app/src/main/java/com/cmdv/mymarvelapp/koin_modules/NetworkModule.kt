package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.data.utils.NetworkHandler
import com.cmdv.mymarvelapp.retorfit.RetrofitManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    single { NetworkHandler(androidContext()) }
    single(named("no_auth")) { RetrofitManager().getInstance() }
}