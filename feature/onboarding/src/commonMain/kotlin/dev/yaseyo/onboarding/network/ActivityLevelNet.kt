package dev.yaseyo.onboarding.network

import kotlinx.serialization.Serializable

@Serializable
internal data class ActivityLevelNet(
    val level: String,
    val label: String,
    val description: String,
    val multiplier: Double,
)
