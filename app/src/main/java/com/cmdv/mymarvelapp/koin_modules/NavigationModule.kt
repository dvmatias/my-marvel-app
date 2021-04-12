package com.cmdv.mymarvelapp.koin_modules

import com.cmdv.core.navigation.Navigator
import com.cmdv.mymarvelapp.navigation.NavigatorImpl
import org.koin.dsl.module

val navigationModule = module {
    single<Navigator> { NavigatorImpl() }
}