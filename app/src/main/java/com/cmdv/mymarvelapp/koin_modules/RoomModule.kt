package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.data.database.CharactersRoomDatabase
import com.cmdv.data.database.FavouriteCharactersRoomDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single { FavouriteCharactersRoomDataBase.getInstance(androidApplication().applicationContext).favoriteCharactersDao }
    single { CharactersRoomDatabase.getInstance(androidApplication().applicationContext).charactersDao }
}