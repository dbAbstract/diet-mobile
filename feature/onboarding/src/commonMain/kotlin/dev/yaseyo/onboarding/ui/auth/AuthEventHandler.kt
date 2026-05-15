package dev.yaseyo.onboarding.ui.auth

interface AuthEventHandler {
    fun onSignInClicked()

    fun onSignUpClicked()

    fun onForgotPasswordClicked()

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
    )

    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
    )
}
