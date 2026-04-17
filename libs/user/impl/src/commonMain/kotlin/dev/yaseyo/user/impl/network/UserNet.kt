package dev.yaseyo.user.impl.network

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import dev.yaseyo.user.api.User
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
internal data class UserNet(
    val id: String,
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
    val createdAt: String,
    val updatedAt: String,
)

internal fun UserNet.toDomain() =
    User(
        id = id,
        name = name,
        sex = Sex.valueOf(sex),
        height = height,
        dateOfBirth = LocalDate.parse(dateOfBirth),
        activityLevel = activityLevel.toActivityLevel(),
        targetWeightKg = targetWeightKg,
        dailyDeficitKcal = dailyDeficitKcal,
        targetProtein = targetProtein,
        targetCarbs = targetCarbs,
        targetFat = targetFat,
        createdAt = kotlin.time.Instant.parse(createdAt),
        updatedAt = kotlin.time.Instant.parse(updatedAt),
    )

internal fun ActivityLevel.toApiString() =
    when (this) {
        ActivityLevel.Sedentary -> "SEDENTARY"
        ActivityLevel.LightlyActive -> "LIGHTLY_ACTIVE"
    }

private fun String.toActivityLevel() =
    when (this) {
        "SEDENTARY" -> ActivityLevel.Sedentary
        "LIGHTLY_ACTIVE" -> ActivityLevel.LightlyActive
        else -> error("Unknown activity level: $this")
    }
