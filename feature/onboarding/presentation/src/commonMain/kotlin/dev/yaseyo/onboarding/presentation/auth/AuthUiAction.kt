package dev.yaseyo.onboarding.presentation.auth

import dev.yaseyo.onboarding.domain.AppState

sealed interface AuthUiAction {
    data class NavigateTo(
        val destination: AppState,
    ) : AuthUiAction
}
