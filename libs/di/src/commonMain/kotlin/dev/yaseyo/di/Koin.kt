package dev.yaseyo.di

import dev.yaseyo.auth.di.authModule
import dev.yaseyo.coroutines.coroutinesModule

internal val appModules = listOf(
    coroutinesModule,
    authModule,
)
