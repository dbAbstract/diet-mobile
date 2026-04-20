package dev.yaseyo.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.auth.api.AuthRepository
import dev.yaseyo.onboarding.domain.AppState
import dev.yaseyo.onboarding.domain.CheckAppStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface AuthUiState {
    data object Idle : AuthUiState

    data object Loading : AuthUiState

    data class Success(
        val appState: AppState,
    ) : AuthUiState

    data class Error(
        val message: String,
    ) : AuthUiState
}

class AuthPresenter(
    private val authRepository: AuthRepository,
    private val checkAppState: CheckAppStateUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val state: StateFlow<AuthUiState> = _state

    fun signIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _state.value = AuthUiState.Loading
            authRepository
                .signInWithEmailAndPassword(email, password)
                .fold(
                    onSuccess = { _state.value = AuthUiState.Success(checkAppState()) },
                    onFailure = { _state.value = AuthUiState.Error(it.message ?: "Sign in failed") },
                )
        }
    }

    fun signUp(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _state.value = AuthUiState.Loading
            authRepository
                .signUpWithEmailAndPassword(email, password)
                .fold(
                    onSuccess = { _state.value = AuthUiState.Success(checkAppState()) },
                    onFailure = { _state.value = AuthUiState.Error(it.message ?: "Sign up failed") },
                )
        }
    }

    fun clearError() {
        _state.value = AuthUiState.Idle
    }
}
