package dev.yaseyo.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.onboarding.domain.AppState
import dev.yaseyo.onboarding.domain.CheckAppStateUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class SplashPresenter(
    private val checkAppState: CheckAppStateUseCase,
) : ViewModel() {
    val destination: StateFlow<AppState?> = emptyFlow<AppState>()
        .onStart { emit(checkAppState()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null,
        )
}
