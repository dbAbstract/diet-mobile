package dev.yaseyo.auth.api

sealed interface AuthState {
    data class SignedIn(val userId: String) : AuthState

    data object SignedOut : AuthState
}