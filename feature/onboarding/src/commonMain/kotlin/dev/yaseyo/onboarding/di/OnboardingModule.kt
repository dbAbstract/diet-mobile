package dev.yaseyo.onboarding.di

import dev.yaseyo.onboarding.ResolveStartDestinationUseCase
import dev.yaseyo.onboarding.ui.AuthViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onboardingModule = module {
    factoryOf(::ResolveStartDestinationUseCase)
    viewModelOf(::AuthViewModel)
}
