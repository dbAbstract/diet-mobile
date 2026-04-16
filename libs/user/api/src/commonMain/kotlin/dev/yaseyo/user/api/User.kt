package dev.yaseyo.user.api

data class User(
    val id: String,
    val name: String,
    val sex: Sex,
    val height: Double,
    val dateOfBirth: String,
    val activityLevel: ActivityLevel,
    val targetWeightKg: Double,
    val dailyDeficitKcal: Double,
    val targetProtein: Double,
    val targetCarbs: Double,
    val targetFat: Double,
    val createdAt: String,
    val updatedAt: String,
)
