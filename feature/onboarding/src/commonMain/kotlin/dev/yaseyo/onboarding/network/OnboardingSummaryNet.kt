package dev.yaseyo.onboarding.network

import kotlinx.serialization.Serializable

@Serializable
internal data class OnboardingSummaryRequestNet(
    val sex: String,
    val height: Double,
    val dateOfBirth: String,
    val currentWeightKg: Double,
    val activityLevel: String,
    val dailyDeficitKcal: Int,
)

@Serializable
internal data class OnboardingSummaryNet(
    val tdee: Double,
    val dailyCalorieTarget: Double,
    val weeklyLossKg: Double,
    val suggestedMacros: SuggestedMacrosNet,
)

@Serializable
internal data class SuggestedMacrosNet(
    val protein: Double,
    val carbs: Double,
    val fat: Double,
)
