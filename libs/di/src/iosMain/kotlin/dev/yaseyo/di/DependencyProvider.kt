package dev.yaseyo.di

import dev.yaseyo.auth.api.AuthRepository
import org.koin.mp.KoinPlatform

fun getAuthRepository(): AuthRepository = KoinPlatform.getKoin().get()
