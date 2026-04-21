package dev.yaseyo.onboarding.presentation.splash

import dev.yaseyo.onboarding.domain.AppState

sealed interface SplashUiAction {
    data class NavigateTo(
        val destination: AppState,
    ) : SplashUiAction
}
