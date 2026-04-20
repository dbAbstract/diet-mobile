package dev.yaseyo.onboarding.domain

import dev.yaseyo.auth.api.AuthRepository
import dev.yaseyo.auth.api.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class FakeAuthRepository(
    authState: AuthState = AuthState.SignedOut,
) : AuthRepository {
    override val authState: StateFlow<AuthState> = MutableStateFlow(authState)

    override suspend fun getIdToken(): String? = null

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ): Result<Unit> = Result.success(Unit)

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
    ): Result<Unit> = Result.success(Unit)

    override suspend fun signOut() = Unit
}
