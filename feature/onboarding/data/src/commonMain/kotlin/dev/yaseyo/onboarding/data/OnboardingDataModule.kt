package dev.yaseyo.onboarding.data

import dev.yaseyo.onboarding.domain.CheckAppStateUseCase
import org.koin.dsl.module

val onboardingDataModule = module {
    factory {
        CheckAppStateUseCase(
            authRepository = get(),
            userRepository = get(),
        )
    }
}
