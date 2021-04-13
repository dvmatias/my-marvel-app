package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.data.source.service.CharactersApi
import com.cmdv.data.source.service.CharactersService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModule = module {
    single<CharactersApi> { CharactersService(get(named("no_auth"))) }
}