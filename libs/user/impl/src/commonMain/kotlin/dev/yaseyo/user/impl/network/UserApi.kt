package dev.yaseyo.user.impl.network

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import kotlinx.datetime.LocalDate

internal interface UserApi {
    suspend fun getUser(): UserNet

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
    ): UserNet

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
    ): UserNet
}
