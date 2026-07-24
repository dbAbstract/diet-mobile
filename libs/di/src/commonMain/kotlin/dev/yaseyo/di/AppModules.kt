package dev.yaseyo.di

import dev.yaseyo.auth.impl.authImplModule
import dev.yaseyo.coroutines.coroutinesModule
import dev.yaseyo.dailylog.impl.dailyLogImplModule
import dev.yaseyo.datastore.dataStoreModule
import dev.yaseyo.home.di.homeModule
import dev.yaseyo.logmeal.di.logMealModule
import dev.yaseyo.navigation.navigationModule
import dev.yaseyo.network.networkModule
import dev.yaseyo.onboarding.di.onboardingModule
import dev.yaseyo.user.impl.userImplModule
import org.koin.dsl.module

internal val appModules = listOf(
    navigationModule,
    coroutinesModule,
    authImplModule,
    userImplModule,
    module { single { networkConfig } },
    networkModule,
    onboardingModule,
    dataStoreModule,
    dailyLogImplModule,
    homeModule,
    logMealModule,
)
