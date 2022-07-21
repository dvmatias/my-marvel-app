package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.feature.characterdetails.CharacterDetailsViewModel
import com.cmdv.feature.characters.fragment.characters.CharactersViewModel
import com.cmdv.feature.characters.fragment.favorites.FavoritesViewModel
import com.cmdv.feature.splash.SplashViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Suppress("EXPERIMENTAL_API_USAGE")
@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { CharactersViewModel(get(), get(), get(), get()) }
    viewModel { FavoritesViewModel(get(), get(), get()) }
    viewModel { CharacterDetailsViewModel(get()) }
}