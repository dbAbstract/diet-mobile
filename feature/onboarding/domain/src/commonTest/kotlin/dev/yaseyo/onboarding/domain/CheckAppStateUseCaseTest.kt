package dev.yaseyo.onboarding.domain

import dev.yaseyo.auth.api.AuthState
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertIs

class CheckAppStateUseCaseTest {
    private fun useCase(
        authRepository: FakeAuthRepository = FakeAuthRepository(),
        userRepository: FakeUserRepository = FakeUserRepository(),
    ) = CheckAppStateUseCase(authRepository, userRepository)

    @Test
    fun `returns Unauthenticated when signed out`(): Unit =
        runBlocking {
            val result = useCase(authRepository = FakeAuthRepository(AuthState.SignedOut)).invoke()
            assertIs<AppState.Unauthenticated>(result)
        }

    @Test
    fun `returns FullySetup when signed in and profile exists`(): Unit =
        runBlocking {
            val result = useCase(
                authRepository = FakeAuthRepository(AuthState.SignedIn(userId = "usr_abc123")),
                userRepository = FakeUserRepository(Result.success(FakeUserRepository.DEFAULT_USER)),
            ).invoke()
            assertIs<AppState.FullySetup>(result)
        }

    @Test
    fun `returns RequiresProfileSetup when signed in but no profile exists`(): Unit =
        runBlocking {
            val result = useCase(
                authRepository = FakeAuthRepository(AuthState.SignedIn(userId = "usr_abc123")),
                userRepository = FakeUserRepository(Result.failure(RuntimeException("not found"))),
            ).invoke()
            assertIs<AppState.RequiresProfileSetup>(result)
        }
}
