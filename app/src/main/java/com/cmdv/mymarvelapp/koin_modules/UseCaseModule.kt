package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.domain.usecase.GetCharactersUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetCharactersUseCase(get()) }
}
