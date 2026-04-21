package dev.yaseyo.onboarding.presentation

import dev.yaseyo.onboarding.presentation.auth.AuthViewModel
import dev.yaseyo.onboarding.presentation.profile.ProfileSetupViewModel
import dev.yaseyo.onboarding.presentation.splash.SplashViewModel
import org.koin.mp.KoinPlatform

fun getSplashViewModel(): SplashViewModel = KoinPlatform.getKoin().get()

fun getAuthViewModel(): AuthViewModel = KoinPlatform.getKoin().get()

fun getProfileSetupViewModel(): ProfileSetupViewModel = KoinPlatform.getKoin().get()
