package dev.yaseyo.onboarding.api

import dev.yaseyo.auth.api.AuthRepository
import dev.yaseyo.auth.api.AuthState
import dev.yaseyo.onboarding.api.model.AppState
import dev.yaseyo.user.api.UserRepository

class ResolveAppStateUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {
    suspend fun execute(): AppState =
        when (authRepository.authState.value) {
            is AuthState.SignedOut -> AppState.RequiresLogin

            is AuthState.SignedIn -> {
                val user = userRepository.getCurrentUser()
                if (user.isSuccess) {
                    AppState.FullySetup
                } else {
                    AppState.RequiresProfileSetup
                }
            }
        }
}
