package dev.yaseyo.onboarding.presentation.profile

sealed interface ProfileSetupUiAction {
    data object NavigateToHome : ProfileSetupUiAction
}
