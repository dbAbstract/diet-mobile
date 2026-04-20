package dev.yaseyo.onboarding.presentation

import org.koin.mp.KoinPlatform

fun getSplashPresenter(): SplashPresenter = KoinPlatform.getKoin().get()

fun getAuthPresenter(): AuthPresenter = KoinPlatform.getKoin().get()

fun getProfileSetupPresenter(): ProfileSetupPresenter = KoinPlatform.getKoin().get()
