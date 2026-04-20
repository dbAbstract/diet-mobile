package dev.yaseyo.onboarding.presentation

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onboardingPresentationModule = module {
    viewModel { SplashPresenter(get()) }
    viewModel { AuthPresenter(get(), get()) }
    viewModel { ProfileSetupPresenter(get()) }
}
