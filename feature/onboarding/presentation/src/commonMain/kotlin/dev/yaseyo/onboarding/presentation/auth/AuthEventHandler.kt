package dev.yaseyo.onboarding.presentation.auth

interface AuthEventHandler {
    fun signIn(
        email: String,
        password: String,
    )

    fun signUp(
        email: String,
        password: String,
    )

    fun clearError()
}
