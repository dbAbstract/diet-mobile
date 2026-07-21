package dev.yaseyo.onboarding.model

internal data class OnboardingSummary(
    val tdee: Int,
    val dailyCalorieTarget: Int,
    val weeklyLossKg: Double,
    val proteinG: Int,
    val carbsG: Int,
    val fatG: Int,
)
