package dev.yaseyo.onboarding.repository

import dev.yaseyo.onboarding.model.ActivityLevelOption
import dev.yaseyo.onboarding.model.GoalSuggestion
import dev.yaseyo.onboarding.model.OnboardingDraft
import dev.yaseyo.onboarding.model.OnboardingStep
import dev.yaseyo.onboarding.model.OnboardingSummary
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

internal interface OnboardingRepository {
    val draft: Flow<OnboardingDraft>

    suspend fun saveDraft(draft: OnboardingDraft)

    suspend fun clearDraft()

    suspend fun getSteps(): Result<List<OnboardingStep>>

    suspend fun getActivityLevels(): Result<List<ActivityLevelOption>>

    suspend fun getGoalSuggestion(
        sex: Sex,
        heightCm: Int,
        dateOfBirth: LocalDate,
        currentWeightKg: Int,
    ): Result<GoalSuggestion>

    suspend fun getSummary(
        sex: Sex,
        heightCm: Int,
        dateOfBirth: LocalDate,
        currentWeightKg: Int,
        activityLevel: ActivityLevel,
        dailyDeficitKcal: Int,
    ): Result<OnboardingSummary>
}
