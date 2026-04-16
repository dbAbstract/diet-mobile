package dev.yaseyo.user.impl.network

import kotlinx.serialization.Serializable

@Serializable
internal data class CreateUserRequestNet(
    val name: String,
    val sex: String,
    val height: Double,
    val dateOfBirth: String,
    val activityLevel: String,
    val targetWeightKg: Double,
    val dailyDeficitKcal: Double,
    val targetProtein: Double,
    val targetCarbs: Double,
    val targetFat: Double,
)
