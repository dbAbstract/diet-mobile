package dev.yaseyo.auth.impl

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.yaseyo.auth.api.AuthRepository
import org.koin.dsl.module

val authImplModule = module {
    single<FirebaseAuth> {
        Firebase.auth
    }
    single<AuthRepository> {
        AuthRepositoryImpl(
            firebaseAuth = get(),
            dispatcherProvider = get(),
        )
    }
}
