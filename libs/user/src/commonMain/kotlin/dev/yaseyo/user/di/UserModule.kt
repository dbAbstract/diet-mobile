package dev.yaseyo.user.di

import dev.yaseyo.user.impl.userImplModule
import org.koin.dsl.module

val userModule = module {
    includes(userImplModule)
}
