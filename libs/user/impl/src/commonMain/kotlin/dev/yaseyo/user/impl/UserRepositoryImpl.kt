package dev.yaseyo.user.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import dev.yaseyo.user.api.User
import dev.yaseyo.user.api.UserRepository
import dev.yaseyo.user.impl.network.UserApi
import dev.yaseyo.user.impl.network.toDomain
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

internal class UserRepositoryImpl(
    private val api: UserApi,
    private val dataStore: DataStore<Preferences>,
) : UserRepository {
    override suspend fun isUserSessionActive(): Boolean =
        runCatching {
            dataStore.data.map { it[Keys.USER_ID_KEY] }.first() == true
        }.getOrDefault(false)

    override suspend fun clearUserSession(): Result<Unit> =
        runCatching {
            dataStore.edit { prefs ->
                prefs[Keys.USER_ID_KEY] = false
            }
        }.map {}

    private object Keys {
        val USER_ID_KEY = booleanPreferencesKey("user_id")
    }

    override suspend fun getCurrentUser(): Result<User> =
        runCatching {
            api.getUser().toDomain()
        }.onSuccess {
            dataStore.edit { prefs ->
                prefs[Keys.USER_ID_KEY] = true
            }
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
