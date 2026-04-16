package dev.yaseyo.user.impl.network

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

internal class UserApiImpl(
    private val client: HttpClient,
) : UserApi {
    companion object {
        private const val USER_PATH = "/user/"
    }

    override suspend fun getUser(): UserNet = client.get(USER_PATH).body()

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
    ): UserNet =
        client
            .post(USER_PATH) {
                setBody(
                    CreateUserRequestNet(
                        name = name,
                        sex = sex.name,
                        height = height,
                        dateOfBirth = dateOfBirth.toString(),
                        activityLevel = activityLevel.toApiString(),
                        targetWeightKg = targetWeightKg,
                        dailyDeficitKcal = dailyDeficitKcal,
                        targetProtein = targetProtein,
                        targetCarbs = targetCarbs,
                        targetFat = targetFat,
                    ),
                )
            }.body()

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
    ): UserNet =
        client
            .patch(USER_PATH) {
                setBody(
                    buildJsonObject {
                        name?.let { put("name", it) }
                        sex?.let { put("sex", it.name) }
                        height?.let { put("height", it) }
                        dateOfBirth?.let { put("dateOfBirth", it.toString()) }
                        activityLevel?.let { put("activityLevel", it.toApiString()) }
                        targetWeightKg?.let { put("targetWeightKg", it) }
                        dailyDeficitKcal?.let { put("dailyDeficitKcal", it) }
                        targetProtein?.let { put("targetProtein", it) }
                        targetCarbs?.let { put("targetCarbs", it) }
                        targetFat?.let { put("targetFat", it) }
                    },
                )
            }.body()
}
