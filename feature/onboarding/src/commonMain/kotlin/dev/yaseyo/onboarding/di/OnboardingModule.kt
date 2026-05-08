package dev.yaseyo.onboarding.di

import dev.yaseyo.onboarding.ResolveAppStateUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val onboardingModule = module {
    factoryOf(::ResolveAppStateUseCase)
}
