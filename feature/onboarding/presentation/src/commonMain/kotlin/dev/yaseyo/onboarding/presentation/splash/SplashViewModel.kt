package dev.yaseyo.onboarding.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.coroutines.DispatcherProvider
import dev.yaseyo.onboarding.domain.CheckAppStateUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class SplashViewModel(
    private val checkAppState: CheckAppStateUseCase,
    private val dispatchers: DispatcherProvider,
) : ViewModel() {
    private val _actions = Channel<SplashUiAction>(Channel.BUFFERED)
    val actions: Flow<SplashUiAction> = _actions.receiveAsFlow()

    val state: StateFlow<SplashUiState> =
        emptyFlow<SplashUiState>()
            .onStart {
                emit(SplashUiState(isLoading = true))
                _actions.send(SplashUiAction.NavigateTo(checkAppState()))
                emit(SplashUiState(isLoading = false))
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = SplashUiState(),
            )
}
