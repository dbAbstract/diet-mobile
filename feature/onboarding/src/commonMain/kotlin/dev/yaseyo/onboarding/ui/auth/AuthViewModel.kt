package dev.yaseyo.onboarding.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.auth.api.AuthRepository
import dev.yaseyo.home.navigation.HomeRoutes
import dev.yaseyo.navigation.AppRouter
import dev.yaseyo.onboarding.navigation.OnboardingRoutes
import dev.yaseyo.user.api.UserRepository
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
    private val userRepository: UserRepository,
) : ViewModel(),
    AuthEventHandler {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _action = Channel<String>(capacity = Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    override fun onSignInClicked() {
        router.navigate(OnboardingRoutes.Auth.SignIn)
    }

    override fun onSignUpClicked() {
        router.navigate(OnboardingRoutes.Auth.SignUp)
    }

    override fun onForgotPasswordClicked() { /* TODO */ }

    override fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ) {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val signInResult = authRepository.signInWithEmailAndPassword(
                email = email,
                password = password,
            )

            signInResult.fold(
                onSuccess = {
                    when {
                        userRepository.isUserSessionActive() -> {
                            router.navigateAndClearBackStack(HomeRoutes.Home)
                        }

                        userRepository.getCurrentUser().isSuccess -> {
                            router.navigateAndClearBackStack(HomeRoutes.Home)
                        }

                        else -> {
                            router.navigateAndClearBackStack(OnboardingRoutes.ProfileSetup)
                        }
                    }
                },
                onFailure = {
                    _action.send("Something went wrong")
                },
            )

            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

    override fun signUpWithEmailAndPassword(
        email: String,
        password: String,
    ) {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val signInResult = authRepository.signUpWithEmailAndPassword(
                email = email,
                password = password,
            )

            signInResult.fold(
                onSuccess = {
                    router.navigateAndClearBackStack(OnboardingRoutes.ProfileSetup)
                },
                onFailure = {
                    _action.send("Something went wrong")
                },
            )

            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }
}
