package dev.yaseyo.di

import dev.yaseyo.auth.di.authModule
import dev.yaseyo.coroutines.coroutinesModule
import dev.yaseyo.network.networkModule
import dev.yaseyo.onboarding.di.onboardingModule
import dev.yaseyo.user.di.userModule
import org.koin.dsl.module

internal val appModules = listOf(
    coroutinesModule,
    authModule,
    userModule,
    module { single { networkConfig } },
    networkModule,
    onboardingModule,
)
