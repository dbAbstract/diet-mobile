package dev.yaseyo.onboarding.model

internal data class GoalSuggestion(
    val currentBmi: Double,
    val currentBodyFatPct: Double?,
    val currentBodyFatCategory: String?,
    val suggestedTargetKg: Int,
)
