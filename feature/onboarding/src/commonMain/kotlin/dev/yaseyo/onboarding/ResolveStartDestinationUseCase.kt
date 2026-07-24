package dev.yaseyo.onboarding

import dev.yaseyo.auth.api.AuthRepository
import dev.yaseyo.auth.api.AuthState
import dev.yaseyo.home.navigation.HomeRoutes
import dev.yaseyo.navigation.AppRoute
import dev.yaseyo.onboarding.model.AppState
import dev.yaseyo.onboarding.navigation.OnboardingRoutes
import dev.yaseyo.user.api.UserRepository

class ResolveStartDestinationUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {
    suspend fun execute(): AppRoute {
        val appState = when (authRepository.authState.value) {
            is AuthState.SignedOut -> AppState.RequiresLogin

            is AuthState.SignedIn if userRepository.isUserSessionActive().isSuccess -> {
                AppState.FullySetup
            }

            else -> {
                val user = userRepository.getCurrentUser()
                if (user.isSuccess) {
                    AppState.FullySetup
                } else {
                    AppState.RequiresProfileSetup
                }
            }
        }

        return when (appState) {
            AppState.FullySetup -> HomeRoutes.Home

            AppState.RequiresLogin -> OnboardingRoutes.Auth.Landing

            AppState.RequiresProfileSetup -> OnboardingRoutes.ProfileSetup
        }
    }
}
