package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.feature.characters.adapter.CharacterAdapter
import org.koin.dsl.module

val adapterModule = module {
    factory { CharacterAdapter() }
}