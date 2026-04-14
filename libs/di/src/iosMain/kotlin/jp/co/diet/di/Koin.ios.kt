package jp.co.diet.di

import org.koin.core.context.startKoin

fun startKoin() {
    startKoin {
        modules(appModules)
    }
}
