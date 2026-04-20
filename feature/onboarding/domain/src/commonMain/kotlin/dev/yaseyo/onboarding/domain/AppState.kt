package dev.yaseyo.onboarding.domain

/**
 * Represents the initial destination the app should route to on launch,
 * determined by [CheckAppStateUseCase].
 */
sealed interface AppState {
    /** No active authentication session — user must sign in or sign up. */
    data object Unauthenticated : AppState

    /** Signed in but no profile exists yet — user must complete the onboarding wizard. */
    data object RequiresProfileSetup : AppState

    /** Signed in with a complete profile — proceed directly to the main app. */
    data object FullySetup : AppState
}
