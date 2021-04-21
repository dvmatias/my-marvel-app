package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.domain.usecase.AddFavoriteCharacterUseCase
import com.cmdv.domain.usecase.GetCharactersUseCase
import com.cmdv.domain.usecase.GetTotalCharactersUseCase
import com.cmdv.domain.usecase.RemoveFavoriteCharacterUseCase
import org.koin.dsl.module

@Suppress("EXPERIMENTAL_API_USAGE")
val useCaseModule = module {
    single { GetTotalCharactersUseCase(get()) }
    single { GetCharactersUseCase(get()) }
    single { AddFavoriteCharacterUseCase(get()) }
    single { RemoveFavoriteCharacterUseCase(get()) }
}
