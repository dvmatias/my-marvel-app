package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.feature.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }
}