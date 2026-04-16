package dev.yaseyo.user.api

interface UserRepository {
    suspend fun getCurrentUser(): Result<User>

    suspend fun createUser(request: CreateUserRequest): Result<User>

    suspend fun updateUser(request: UpdateUserRequest): Result<User>

    data class CreateUserRequest(
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
    )

    data class UpdateUserRequest(
        val name: String? = null,
        val sex: Sex? = null,
        val height: Double? = null,
        val dateOfBirth: String? = null,
        val activityLevel: ActivityLevel? = null,
        val targetWeightKg: Double? = null,
        val dailyDeficitKcal: Double? = null,
        val targetProtein: Double? = null,
        val targetCarbs: Double? = null,
        val targetFat: Double? = null,
    )
}
