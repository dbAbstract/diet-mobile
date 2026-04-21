package dev.yaseyo.onboarding.presentation.profile

import dev.yaseyo.onboarding.domain.OnboardingProfileDraft

enum class ProfileSetupStep { PersonalInfo, Goals }

data class ProfileSetupUiState(
    val draft: OnboardingProfileDraft = OnboardingProfileDraft(),
    val step: ProfileSetupStep = ProfileSetupStep.PersonalInfo,
    val isLoading: Boolean = false,
    val error: String? = null,
)
