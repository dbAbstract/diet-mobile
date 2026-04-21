package dev.yaseyo.di

import dev.yaseyo.MainViewModel
import dev.yaseyo.onboarding.api.ResolveAppStateUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { ResolveAppStateUseCase(get(), get()) }
    viewModel { MainViewModel(get()) }
}
