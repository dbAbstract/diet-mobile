package dev.yaseyo.auth.api

import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val authState: StateFlow<AuthState>

    suspend fun getIdToken(): String?

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ): Result<Unit>

    fun signOut(): Unit
}