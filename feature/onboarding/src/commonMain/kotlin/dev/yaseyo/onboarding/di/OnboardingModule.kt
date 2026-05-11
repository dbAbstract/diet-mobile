package dev.yaseyo.onboarding.di

import dev.yaseyo.onboarding.ResolveStartDestinationUseCase
import dev.yaseyo.onboarding.network.OnboardingApi
import dev.yaseyo.onboarding.network.OnboardingApiImpl
import dev.yaseyo.onboarding.ui.AuthViewModel
import dev.yaseyo.onboarding.ui.profilesetup.ProfileSetupViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onboardingModule = module {
    factoryOf(::ResolveStartDestinationUseCase)
    viewModelOf(::AuthViewModel)

    single<OnboardingApi> { OnboardingApiImpl(client = get()) }
    viewModelOf(::ProfileSetupViewModel)
}
