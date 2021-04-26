package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.domain.usecase.*
import org.koin.dsl.module

@Suppress("EXPERIMENTAL_API_USAGE")
val useCaseModule = module {
    factory { GetTotalCharactersUseCase(get()) }
    factory { GetCharactersUseCase(get()) }
    factory { AddFavoriteCharacterUseCase(get()) }
    factory { RemoveFavoriteCharacterUseCase(get()) }
    factory { GetFavoriteCharactersUseCase(get()) }
    factory { RemoveAllFavoriteCharacterUseCase(get()) }
}
