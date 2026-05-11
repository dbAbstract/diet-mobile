package dev.yaseyo.di

import dev.yaseyo.auth.api.AuthRepository
import dev.yaseyo.onboarding.ResolveStartDestinationUseCase
import org.koin.mp.KoinPlatform

fun getAuthRepository(): AuthRepository = KoinPlatform.getKoin().get()

fun getResolveStartDestinationUseCase(): ResolveStartDestinationUseCase = KoinPlatform.getKoin().get()
