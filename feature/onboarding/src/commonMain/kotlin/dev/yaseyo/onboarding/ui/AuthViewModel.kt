package dev.yaseyo.onboarding.ui

import androidx.lifecycle.ViewModel
import dev.yaseyo.navigation.AppRouter
import dev.yaseyo.navigation.Home
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel(
    private val router: AppRouter,
) : ViewModel(),
    AuthEventHandler {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    override fun onTabChanged(tab: AuthTab) = _uiState.update { it.copy(tab = tab) }

    override fun onEmailChanged(email: String) = _uiState.update { it.copy(email = email) }

    override fun onPasswordChanged(password: String) = _uiState.update { it.copy(password = password) }

    override fun onPasswordVisibilityToggled() = _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }

    override fun onForgotPasswordClicked() { /* TODO */ }

    override fun onSubmitClicked() {
        // TODO: call auth use case, navigate on success
        router.navigate(Home)
    }
}
