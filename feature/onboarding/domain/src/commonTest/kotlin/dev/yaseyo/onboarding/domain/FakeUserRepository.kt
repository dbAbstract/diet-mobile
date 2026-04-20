package dev.yaseyo.onboarding.domain

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import dev.yaseyo.user.api.User
import dev.yaseyo.user.api.UserRepository
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

internal class FakeUserRepository(
    private val userResult: Result<User> = Result.success(DEFAULT_USER),
) : UserRepository {
    override suspend fun getCurrentUser(): Result<User> = userResult

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
    ): Result<User> = userResult

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
    ): Result<User> = userResult

    companion object {
        val DEFAULT_USER = User(
            id = "usr_abc123",
            name = "Alex Smith",
            sex = Sex.MALE,
            height = 178.0,
            dateOfBirth = LocalDate(1990, 6, 15),
            activityLevel = ActivityLevel.LightlyActive,
            targetWeightKg = 80.0,
            dailyDeficitKcal = 500.0,
            targetProtein = 160.0,
            targetCarbs = 200.0,
            targetFat = 70.0,
            createdAt = Instant.parse("2024-01-10T08:00:00Z"),
            updatedAt = Instant.parse("2024-03-20T14:30:00Z"),
        )
    }
}
