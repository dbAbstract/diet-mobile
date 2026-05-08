package dev.yaseyo.di

import dev.yaseyo.auth.api.AuthRepository
import dev.yaseyo.onboarding.ResolveAppStateUseCase
import org.koin.mp.KoinPlatform

fun getAuthRepository(): AuthRepository = KoinPlatform.getKoin().get()

fun getResolveAppStateUseCase(): ResolveAppStateUseCase = KoinPlatform.getKoin().get()
