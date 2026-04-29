package dev.yaseyo.onboarding.api.model

sealed interface AppState {
    data object FullySetup : AppState

    data object RequiresProfileSetup : AppState

    data object RequiresLogin : AppState
}
