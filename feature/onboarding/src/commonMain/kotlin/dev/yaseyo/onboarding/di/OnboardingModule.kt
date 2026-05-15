package dev.yaseyo.onboarding.di

import dev.yaseyo.onboarding.ResolveStartDestinationUseCase
import dev.yaseyo.onboarding.datasource.OnboardingLocalDataSource
import dev.yaseyo.onboarding.datasource.OnboardingLocalDataSourceImpl
import dev.yaseyo.onboarding.network.OnboardingApi
import dev.yaseyo.onboarding.network.OnboardingApiImpl
import dev.yaseyo.onboarding.repository.OnboardingRepository
import dev.yaseyo.onboarding.repository.OnboardingRepositoryImpl
import dev.yaseyo.onboarding.ui.auth.AuthViewModel
import dev.yaseyo.onboarding.ui.profilesetup.ProfileSetupViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val onboardingModule = module {
    factoryOf(::ResolveStartDestinationUseCase)
    viewModelOf(::AuthViewModel)

    single<OnboardingApi> { OnboardingApiImpl(client = get()) }
    single { OnboardingLocalDataSourceImpl(dataStore = get()) } bind OnboardingLocalDataSource::class
    single { OnboardingRepositoryImpl(localDataSource = get(), api = get()) } bind OnboardingRepository::class
    viewModelOf(::ProfileSetupViewModel)
}
