@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.cmdv.mymarvelapp.koin_modules

import androidx.lifecycle.SavedStateHandle
import com.cmdv.feature.characters.fragment.CharactersViewModel
import com.cmdv.feature.characters.fragment.FavoritesViewModel
import com.cmdv.feature.splash.SplashViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { CharactersViewModel(get(), get(), get(), get()) }
    viewModel { FavoritesViewModel(get(), get(), get()) }
}