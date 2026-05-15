package dev.yaseyo.onboarding.model

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex

internal data class OnboardingDraft(
    val currentStepIndex: Int = 0,
    val name: String = "",
    val sex: Sex? = null,
    val dobDay: Int = 1,
    val dobMonth: Int = 1,
    val dobYear: Int = 1990,
    val heightCm: Int = 170,
    val currentWeightKg: Int = 70,
    val activityLevel: ActivityLevel? = null,
    val targetWeightKg: Int = 70,
    val dailyDeficitKcal: Int = 500,
)
