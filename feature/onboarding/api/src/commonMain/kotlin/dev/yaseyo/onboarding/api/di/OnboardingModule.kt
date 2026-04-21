package dev.yaseyo.onboarding.api.di

import dev.yaseyo.onboarding.api.ResolveAppStateUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val onboardingModule = module {
    factoryOf(::ResolveAppStateUseCase)
}
