package dev.yaseyo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.navigation.AppRoute
import dev.yaseyo.navigation.Home
import dev.yaseyo.onboarding.api.ResolveAppStateUseCase
import dev.yaseyo.onboarding.api.model.AppState
import dev.yaseyo.onboarding.api.navigation.OnboardingRoutes
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val resolveAppStateUseCase: ResolveAppStateUseCase,
) : ViewModel() {
    val startDestination: StateFlow<AppRoute?> = flow {
        val appState = resolveAppStateUseCase.execute()

        emit(
            value = appState.let {
                when (it) {
                    AppState.FullySetup -> Home

                    AppState.RequiresLogin -> OnboardingRoutes.Auth

                    AppState.RequiresProfileSetup -> OnboardingRoutes.ProfileSetup
                }
            },
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = null,
        started = SharingStarted.Eagerly,
    )
}
