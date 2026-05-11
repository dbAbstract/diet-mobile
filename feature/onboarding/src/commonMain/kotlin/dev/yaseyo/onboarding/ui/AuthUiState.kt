package dev.yaseyo.onboarding.ui

enum class AuthTab { SignIn, SignUp }

data class AuthUiState(
    val tab: AuthTab = AuthTab.SignIn,
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
)
