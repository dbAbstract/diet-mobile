package dev.yaseyo.user.impl

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import dev.yaseyo.user.api.User
import dev.yaseyo.user.api.UserRepository
import dev.yaseyo.user.impl.network.UserApi
import dev.yaseyo.user.impl.network.toDomain
import kotlinx.datetime.LocalDate

internal class UserRepositoryImpl(
    private val api: UserApi,
) : UserRepository {
    override suspend fun getCurrentUser(): Result<User> =
        runCatching {
            api.getUser().toDomain()
        }

    override suspend fun createUser(
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
    ): Result<User> =
        runCatching {
            api
                .createUser(
                    name = name,
                    sex = sex,
                    height = height,
                    dateOfBirth = dateOfBirth,
                    activityLevel = activityLevel,
                    targetWeightKg = targetWeightKg,
                    dailyDeficitKcal = dailyDeficitKcal,
                    targetProtein = targetProtein,
                    targetCarbs = targetCarbs,
                    targetFat = targetFat,
                ).toDomain()
        }

    override suspend fun updateUser(
        name: String?,
        sex: Sex?,
        height: Double?,
        dateOfBirth: LocalDate?,
        activityLevel: ActivityLevel?,
        targetWeightKg: Double?,
        dailyDeficitKcal: Double?,
        targetProtein: Double?,
        targetCarbs: Double?,
        targetFat: Double?,
    ): Result<User> =
        runCatching {
            api
                .updateUser(
                    name = name,
                    sex = sex,
                    height = height,
                    dateOfBirth = dateOfBirth,
                    activityLevel = activityLevel,
                    targetWeightKg = targetWeightKg,
                    dailyDeficitKcal = dailyDeficitKcal,
                    targetProtein = targetProtein,
                    targetCarbs = targetCarbs,
                    targetFat = targetFat,
                ).toDomain()
        }
}
