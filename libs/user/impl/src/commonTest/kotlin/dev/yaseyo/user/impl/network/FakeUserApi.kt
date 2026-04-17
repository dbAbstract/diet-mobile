package dev.yaseyo.user.impl.network

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import kotlinx.datetime.LocalDate

internal class FakeUserApi(
    private val response: UserNet = DEFAULT_USER_NET,
    private val error: Throwable? = null,
) : UserApi {
    private fun resolve(): UserNet = error?.let { throw it } ?: response

    override suspend fun getUser(): UserNet = resolve()

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
    ): UserNet = resolve()

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
    ): UserNet = resolve()

    companion object {
        val DEFAULT_USER_NET = UserNet(
            id = "usr_abc123",
            name = "Alex Smith",
            sex = "MALE",
            height = 178.0,
            dateOfBirth = "1990-06-15",
            activityLevel = "LIGHTLY_ACTIVE",
            targetWeightKg = 80.0,
            dailyDeficitKcal = 500.0,
            targetProtein = 160.0,
            targetCarbs = 200.0,
            targetFat = 70.0,
            createdAt = "2024-01-10T08:00:00Z",
            updatedAt = "2024-03-20T14:30:00Z",
        )
    }
}
