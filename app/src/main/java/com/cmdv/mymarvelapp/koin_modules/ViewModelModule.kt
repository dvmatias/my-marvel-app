package com.cmdv.mymarvelapp.koin_modules

import androidx.lifecycle.SavedStateHandle
import com.cmdv.feature.characters.CharactersViewModel
import com.cmdv.feature.splash.SplashViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel { SplashViewModel() }
    single { (handle: SavedStateHandle) -> CharactersViewModel(get(), handle) }
}