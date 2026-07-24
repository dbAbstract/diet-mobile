package dev.yaseyo.user.api

import kotlinx.datetime.LocalDate

interface UserRepository {
    suspend fun isUserSessionActive(): Result<Unit>

    suspend fun clearUserSession(): Result<Unit>

    suspend fun getCurrentUser(): Result<User>

    suspend fun createUser(
        name: String,
        sex: Sex,
        height: Double,
        dateOfBirth: LocalDate,
        activityLevel: ActivityLevel,
        targetWeightKg: Double,
        dailyDeficitKcal: Double,
        targetProtein: Double,
        targetCarbs: Double,
        targetFat: Double,
    ): Result<User>

    suspend fun updateUser(
        name: String? = null,
        sex: Sex? = null,
        height: Double? = null,
        dateOfBirth: LocalDate? = null,
        activityLevel: ActivityLevel? = null,
        targetWeightKg: Double? = null,
        dailyDeficitKcal: Double? = null,
        targetProtein: Double? = null,
        targetCarbs: Double? = null,
        targetFat: Double? = null,
    ): Result<User>
}
