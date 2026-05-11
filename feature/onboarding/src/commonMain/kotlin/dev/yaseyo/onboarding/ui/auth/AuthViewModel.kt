package dev.yaseyo.onboarding.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.auth.api.AuthRepository
import dev.yaseyo.navigation.AppRouter
import dev.yaseyo.onboarding.navigation.OnboardingRoutes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AuthViewModel(
    private val router: AppRouter,
    private val authRepository: AuthRepository,
) : ViewModel(),
    AuthEventHandler {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _action = Channel<String>(capacity = Channel.UNLIMITED)
    val action = _action.receiveAsFlow()

    override fun onTabChanged(tab: AuthTab) = _uiState.update { it.copy(tab = tab) }

    override fun onEmailChanged(email: String) = _uiState.update { it.copy(email = email) }

    override fun onPasswordChanged(password: String) = _uiState.update { it.copy(password = password) }

    override fun onPasswordVisibilityToggled() = _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }

    override fun onForgotPasswordClicked() { /* TODO */ }

    override fun onSubmitClicked() {
        when (uiState.value.tab) {
            AuthTab.SignIn -> {
                viewModelScope.launch {
                    val signInResult = authRepository.signInWithEmailAndPassword(
                        email = uiState.value.email,
                        password = uiState.value.password,
                    )

                    signInResult.fold(
                        onSuccess = {
                            router.navigate(route = OnboardingRoutes.ProfileSetup)
                        },
                        onFailure = {
                            _action.send("Something went wrong")
                        },
                    )
                }
            }
            AuthTab.SignUp -> {
                viewModelScope.launch {
                    val signUpResult = authRepository.signUpWithEmailAndPassword(
                        email = uiState.value.email,
                        password = uiState.value.password,
                    )

                    signUpResult.fold(
                        onSuccess = {
                            router.navigate(route = OnboardingRoutes.ProfileSetup)
                        },
                        onFailure = {
                            _action.send("Something went wrong")
                        },
                    )
                }
            }
        }
    }
}
