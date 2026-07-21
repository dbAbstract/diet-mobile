package dev.yaseyo.onboarding.network

import kotlinx.serialization.Serializable

@Serializable
internal data class GoalSuggestionNet(
    val suggestedTargetKg: Double,
    val current: GoalStatsNet,
)

@Serializable
internal data class GoalStatsNet(
    val weightKg: Double,
    val bmi: Double,
    val bodyFat: BodyFatNet? = null,
)

@Serializable
internal data class BodyFatNet(
    val estimatedPct: Double,
    val category: String,
)
