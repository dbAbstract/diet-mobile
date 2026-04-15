package dev.yaseyo.di

import dev.yaseyo.coroutines.coroutinesModule
import org.koin.core.module.Module

internal val appModules = listOf<Module>(
    coroutinesModule,
)
