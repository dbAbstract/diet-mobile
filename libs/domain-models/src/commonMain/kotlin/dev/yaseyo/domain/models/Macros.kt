package dev.yaseyo.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Macros(
    val kcal: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val fiber: Double,
)
