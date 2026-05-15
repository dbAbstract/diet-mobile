package dev.yaseyo.navigation

import org.koin.dsl.module

val navigationModule = module {
    single { AppRouter() }
}
