package dev.yaseyo.onboarding.domain

import dev.yaseyo.auth.api.AuthRepository
import dev.yaseyo.auth.api.AuthState
import dev.yaseyo.user.api.UserRepository

class CheckAppStateUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): AppState =
        when (authRepository.authState.value) {
            is AuthState.SignedOut -> AppState.Unauthenticated
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
