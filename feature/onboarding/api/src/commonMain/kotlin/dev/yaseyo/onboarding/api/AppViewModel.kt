package dev.yaseyo.onboarding.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.onboarding.api.model.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    private val resolveAppStateUseCase: ResolveAppStateUseCase,
) : ViewModel() {
    private val _appState = MutableStateFlow<AppState?>(null)
    val appState = _appState
        .onStart {
            viewModelScope.launch {
                _appState.value = resolveAppStateUseCase.execute()
            }
        }.stateIn(
            scope = viewModelScope,
            initialValue = null,
            started = SharingStarted.WhileSubscribed(5_000),
        )
}
