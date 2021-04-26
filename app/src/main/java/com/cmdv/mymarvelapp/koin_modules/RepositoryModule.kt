package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.data.repository.CharacterRepositoryImpl
import com.cmdv.data.repository.FavoriteCharacterRepositoryImpl
import com.cmdv.domain.repository.CharacterRepository
import com.cmdv.domain.repository.FavoriteCharacterRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get()) }
    factory<FavoriteCharacterRepository> { FavoriteCharacterRepositoryImpl(get()) }
}