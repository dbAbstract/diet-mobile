package dev.yaseyo.onboarding.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.auth.api.AuthRepository
import dev.yaseyo.coroutines.DispatcherProvider
import dev.yaseyo.onboarding.domain.CheckAppStateUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val checkAppState: CheckAppStateUseCase,
    private val dispatchers: DispatcherProvider,
) : ViewModel(),
    AuthEventHandler {
    private val _state = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val state: StateFlow<AuthUiState> = _state

    private val _actions = Channel<AuthUiAction>(Channel.BUFFERED)
    val actions: Flow<AuthUiAction> = _actions.receiveAsFlow()

    override fun signIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch(dispatchers.io) {
            _state.value = AuthUiState.Loading
            authRepository
                .signInWithEmailAndPassword(email, password)
                .fold(
                    onSuccess = { _actions.send(AuthUiAction.NavigateTo(checkAppState())) },
                    onFailure = { _state.value = AuthUiState.Error(it.message ?: "Sign in failed") },
                )
        }
    }

    override fun signUp(
        email: String,
        password: String,
    ) {
        viewModelScope.launch(dispatchers.io) {
            _state.value = AuthUiState.Loading
            authRepository
                .signUpWithEmailAndPassword(email, password)
                .fold(
                    onSuccess = { _actions.send(AuthUiAction.NavigateTo(checkAppState())) },
                    onFailure = { _state.value = AuthUiState.Error(it.message ?: "Sign up failed") },
                )
        }
    }

    override fun clearError() {
        _state.value = AuthUiState.Idle
    }
}
