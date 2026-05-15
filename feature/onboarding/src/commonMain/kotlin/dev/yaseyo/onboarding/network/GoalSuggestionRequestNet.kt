package dev.yaseyo.onboarding.network

import kotlinx.serialization.Serializable

@Serializable
internal data class GoalSuggestionRequestNet(
    val sex: String,
    val height: Double,
    val dateOfBirth: String,
    val currentWeightKg: Double,
)
