package dev.yaseyo.onboarding.ui.profilesetup

import dev.yaseyo.onboarding.model.OnboardingStep

internal data class ProfileSetupUiState(
    val steps: List<OnboardingStep> = emptyList(),
    val isLoadingSteps: Boolean = true,
    val currentStepIndex: Int = 0,
    val name: String = "",
)

internal val ProfileSetupUiState.totalSteps: Int
    get() = steps.size.takeIf { it > 0 } ?: 6

internal val ProfileSetupUiState.currentStep: OnboardingStep?
    get() = steps.getOrNull(currentStepIndex)

internal val ProfileSetupUiState.progressFraction: Float
    get() = (currentStepIndex + 1).toFloat() / totalSteps
