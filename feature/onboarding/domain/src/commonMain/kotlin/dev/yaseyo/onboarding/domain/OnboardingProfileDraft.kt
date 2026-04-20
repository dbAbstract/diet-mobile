package dev.yaseyo.onboarding.domain

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import kotlinx.datetime.LocalDate

data class OnboardingProfileDraft(
    val name: String = "",
    val sex: Sex? = null,
    val height: Double? = null,
    val dateOfBirth: LocalDate? = null,
    val activityLevel: ActivityLevel? = null,
    val targetWeightKg: Double? = null,
    val dailyDeficitKcal: Double? = null,
    val targetProtein: Double? = null,
    val targetCarbs: Double? = null,
    val targetFat: Double? = null,
)
