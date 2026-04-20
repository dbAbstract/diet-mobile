package dev.yaseyo.user

import dev.yaseyo.user.api.UserRepository
import org.koin.mp.KoinPlatform

fun getUserRepository(): UserRepository = KoinPlatform.getKoin().get()
