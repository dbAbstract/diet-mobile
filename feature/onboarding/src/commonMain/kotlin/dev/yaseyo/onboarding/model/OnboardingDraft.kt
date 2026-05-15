package dev.yaseyo.onboarding.model

internal data class OnboardingDraft(
    val currentStepIndex: Int = 0,
    val name: String = "",
    val sex: String? = null,
    val dobDay: Int = 1,
    val dobMonth: Int = 1,
    val dobYear: Int = 1990,
    val heightCm: Int = 170,
    val currentWeightKg: Int = 70,
    val activityLevel: String? = null,
    val targetWeightKg: Int = 70,
    val dailyDeficitKcal: Int = 500,
)
