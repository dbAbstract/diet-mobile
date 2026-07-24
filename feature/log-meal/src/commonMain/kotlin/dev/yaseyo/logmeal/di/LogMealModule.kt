package dev.yaseyo.logmeal.di

import dev.yaseyo.logmeal.ui.LogMealViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val logMealModule = module {
    viewModelOf(::LogMealViewModel)
}
