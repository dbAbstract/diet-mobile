package dev.yaseyo.coroutines

import org.koin.dsl.bind
import org.koin.dsl.module

val coroutinesModule = module {
    single {
        DispatcherProviderImpl()
    } bind DispatcherProvider::class
}
