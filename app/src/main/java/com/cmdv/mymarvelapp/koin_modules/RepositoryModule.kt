package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.data.repository.CharactersRepositoryImpl
import com.cmdv.domain.repository.CharactersRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<CharactersRepository> { CharactersRepositoryImpl(get(), get(), get()) }
}