package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.data.database.FavouriteCharactersRoomDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    factory { FavouriteCharactersRoomDataBase.getInstance(androidApplication().applicationContext).favouriteCharactersDao }
}