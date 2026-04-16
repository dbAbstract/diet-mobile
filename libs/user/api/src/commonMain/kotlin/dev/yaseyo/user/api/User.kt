package dev.yaseyo.user.api

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class User(
    val id: String,
    val name: String,
    val sex: Sex,
    val height: Double,
    val dateOfBirth: LocalDate,
    val activityLevel: ActivityLevel,
    val targetWeightKg: Double,
    val dailyDeficitKcal: Double,
    val targetProtein: Double,
    val targetCarbs: Double,
    val targetFat: Double,
    val createdAt: Instant,
    val updatedAt: Instant,
)
