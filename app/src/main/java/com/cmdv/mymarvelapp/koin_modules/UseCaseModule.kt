package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.domain.usecase.AddFavouriteCharacterUseCase
import com.cmdv.domain.usecase.GetCharactersUseCase
import com.cmdv.domain.usecase.RemoveFavouriteCharacterUseCase
import org.koin.dsl.module

@Suppress("EXPERIMENTAL_API_USAGE")
val useCaseModule = module {
    single { GetCharactersUseCase(get()) }
    single { AddFavouriteCharacterUseCase(get()) }
    single { RemoveFavouriteCharacterUseCase(get()) }
}
