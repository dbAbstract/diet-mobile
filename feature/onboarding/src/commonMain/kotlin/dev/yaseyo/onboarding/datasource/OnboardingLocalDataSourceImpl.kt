package dev.yaseyo.onboarding.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.yaseyo.onboarding.model.OnboardingDraft
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class OnboardingLocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : OnboardingLocalDataSource {
    override val draft: Flow<OnboardingDraft> = dataStore.data.map { prefs ->
        OnboardingDraft(
            currentStepIndex = prefs[Keys.CURRENT_STEP_INDEX] ?: 0,
            name = prefs[Keys.NAME] ?: "",
            sex = prefs[Keys.SEX]?.toSex(),
            dobDay = prefs[Keys.DOB_DAY] ?: 1,
            dobMonth = prefs[Keys.DOB_MONTH] ?: 1,
            dobYear = prefs[Keys.DOB_YEAR] ?: 1990,
            heightCm = prefs[Keys.HEIGHT_CM] ?: 170,
            currentWeightKg = prefs[Keys.CURRENT_WEIGHT_KG] ?: 70,
            activityLevel = prefs[Keys.ACTIVITY_LEVEL]?.toActivityLevel(),
            targetWeightKg = prefs[Keys.TARGET_WEIGHT_KG] ?: 70,
            dailyDeficitKcal = prefs[Keys.DAILY_DEFICIT_KCAL] ?: 500,
        )
    }

    override suspend fun saveDraft(draft: OnboardingDraft) {
        dataStore.edit { prefs ->
            prefs[Keys.CURRENT_STEP_INDEX] = draft.currentStepIndex
            prefs[Keys.NAME] = draft.name
            draft.sex?.let { prefs[Keys.SEX] = it.toStorageValue() } ?: prefs.remove(Keys.SEX)
            prefs[Keys.DOB_DAY] = draft.dobDay
            prefs[Keys.DOB_MONTH] = draft.dobMonth
            prefs[Keys.DOB_YEAR] = draft.dobYear
            prefs[Keys.HEIGHT_CM] = draft.heightCm
            prefs[Keys.CURRENT_WEIGHT_KG] = draft.currentWeightKg
            draft.activityLevel?.let { prefs[Keys.ACTIVITY_LEVEL] = it.toStorageValue() } ?: prefs.remove(Keys.ACTIVITY_LEVEL)
            prefs[Keys.TARGET_WEIGHT_KG] = draft.targetWeightKg
            prefs[Keys.DAILY_DEFICIT_KCAL] = draft.dailyDeficitKcal
        }
    }

    override suspend fun clearDraft() {
        dataStore.edit { it.clear() }
    }

    private fun String.toSex(): Sex? =
        when (this) {
            "MALE" -> Sex.Male
            "FEMALE" -> Sex.Female
            else -> null
        }

    private fun String.toActivityLevel(): ActivityLevel? =
        when (this) {
            "SEDENTARY" -> ActivityLevel.Sedentary
            "LIGHTLY_ACTIVE" -> ActivityLevel.LightlyActive
            else -> null
        }

    private fun Sex.toStorageValue() =
        when (this) {
            Sex.Male -> "MALE"
            Sex.Female -> "FEMALE"
        }

    private fun ActivityLevel.toStorageValue() =
        when (this) {
            ActivityLevel.Sedentary -> "SEDENTARY"
            ActivityLevel.LightlyActive -> "LIGHTLY_ACTIVE"
        }

    private object Keys {
        val CURRENT_STEP_INDEX = intPreferencesKey("current_step_index")
        val NAME = stringPreferencesKey("name")
        val SEX = stringPreferencesKey("sex")
        val DOB_DAY = intPreferencesKey("dob_day")
        val DOB_MONTH = intPreferencesKey("dob_month")
        val DOB_YEAR = intPreferencesKey("dob_year")
        val HEIGHT_CM = intPreferencesKey("height_cm")
        val CURRENT_WEIGHT_KG = intPreferencesKey("current_weight_kg")
        val ACTIVITY_LEVEL = stringPreferencesKey("activity_level")
        val TARGET_WEIGHT_KG = intPreferencesKey("target_weight_kg")
        val DAILY_DEFICIT_KCAL = intPreferencesKey("daily_deficit_kcal")
    }
}
