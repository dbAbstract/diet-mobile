package dev.yaseyo.auth.impl

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.yaseyo.auth.api.AuthRepository
import dev.yaseyo.auth.api.AuthState
import dev.yaseyo.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    dispatcherProvider: DispatcherProvider,
) : AuthRepository {
    private val scope = CoroutineScope(dispatcherProvider.io)

    override val authState: StateFlow<AuthState> =
        firebaseAuth.authStateChanged.map { firebaseUser ->
            resolveFirebaseUserToAuthState(firebaseUser = firebaseUser)
        }.stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = resolveFirebaseUserToAuthState(firebaseUser = firebaseAuth.currentUser),
        )

    override suspend fun getIdToken(): String? {
        val currentFirebaseUser = firebaseAuth.currentUser
        val isSignedIn = currentFirebaseUser != null

        return if (isSignedIn) {
            val currentToken = currentFirebaseUser.getIdToken(forceRefresh = false)
            currentToken ?: currentFirebaseUser.getIdToken(forceRefresh = true)
        } else {
            null
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Result<Unit> = runCatching {
        firebaseAuth.signInWithEmailAndPassword(
            email = email,
            password = password,
        )
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    private fun resolveFirebaseUserToAuthState(firebaseUser: FirebaseUser?): AuthState {
        return if (firebaseUser == null) {
            AuthState.SignedOut
        } else {
            AuthState.SignedIn(userId = firebaseUser.uid)
        }
    }
}