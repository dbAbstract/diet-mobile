package dev.yaseyo.di

import dev.yaseyo.auth.di.authModule
import dev.yaseyo.coroutines.coroutinesModule
import dev.yaseyo.network.networkModule
import org.koin.dsl.module

internal val appModules = listOf(
    coroutinesModule,
    authModule,
    module { single { networkConfig } },
    networkModule,
)
