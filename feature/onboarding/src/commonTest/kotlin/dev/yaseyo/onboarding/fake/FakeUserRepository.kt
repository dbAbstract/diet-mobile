package dev.yaseyo.onboarding.fake

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import dev.yaseyo.user.api.User
import dev.yaseyo.user.api.UserRepository
import kotlinx.datetime.LocalDate
import kotlin.time.Instant

internal class FakeUserRepository(
    private val createUserResult: Result<User> = Result.success(DEFAULT_USER),
) : UserRepository {
    override suspend fun getCurrentUser(): Result<User> = Result.success(DEFAULT_USER)

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
    ): Result<User> = createUserResult

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
    ): Result<User> = Result.success(DEFAULT_USER)

    companion object {
        val DEFAULT_USER = User(
            id = "usr_test",
            name = "Alex",
            sex = Sex.Male,
            height = 178.0,
            dateOfBirth = LocalDate(1990, 6, 15),
            activityLevel = ActivityLevel.LightlyActive,
            targetWeightKg = 75.0,
            dailyDeficitKcal = 500.0,
            targetProtein = 150.0,
            targetCarbs = 180.0,
            targetFat = 60.0,
            createdAt = Instant.parse("2024-01-10T08:00:00Z"),
            updatedAt = Instant.parse("2024-01-10T08:00:00Z"),
        )
    }
}
