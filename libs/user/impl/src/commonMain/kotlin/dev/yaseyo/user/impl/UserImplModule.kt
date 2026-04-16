package dev.yaseyo.user.impl

import dev.yaseyo.user.api.UserRepository
import dev.yaseyo.user.impl.network.UserApi
import dev.yaseyo.user.impl.network.UserApiImpl
import org.koin.dsl.module

val userImplModule = module {
    single<UserApi> {
        UserApiImpl(client = get())
    }
    single<UserRepository> {
        UserRepositoryImpl(api = get())
    }
}
