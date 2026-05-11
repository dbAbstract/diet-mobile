package dev.yaseyo.onboarding.ui

interface AuthEventHandler {
    fun onTabChanged(tab: AuthTab)

    fun onEmailChanged(email: String)

    fun onPasswordChanged(password: String)

    fun onPasswordVisibilityToggled()

    fun onSubmitClicked()

    fun onForgotPasswordClicked()
}
