package dev.yaseyo.onboarding.presentation

import dev.yaseyo.onboarding.presentation.auth.AuthViewModel
import dev.yaseyo.onboarding.presentation.profile.ProfileSetupViewModel
import dev.yaseyo.onboarding.presentation.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onboardingPresentationModule = module {
    viewModel { SplashViewModel(get(), get()) }
    viewModel { AuthViewModel(get(), get(), get()) }
    viewModel { ProfileSetupViewModel(get(), get()) }
}
