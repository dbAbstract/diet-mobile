package dev.yaseyo.auth.di

import dev.yaseyo.auth.impl.authImplModule
import org.koin.dsl.module

val authModule = module {
    includes(authImplModule)
}
