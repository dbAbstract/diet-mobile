package dev.yaseyo.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MacroTarget(
    val kcal: Double,
    val effectiveKcal: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
)
