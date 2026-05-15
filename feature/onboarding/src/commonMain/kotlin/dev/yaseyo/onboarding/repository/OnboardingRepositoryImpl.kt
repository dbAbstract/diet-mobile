package dev.yaseyo.onboarding.repository

import dev.yaseyo.onboarding.datasource.OnboardingLocalDataSource
import dev.yaseyo.onboarding.model.ActivityLevelOption
import dev.yaseyo.onboarding.model.GoalSuggestion
import dev.yaseyo.onboarding.model.OnboardingDraft
import dev.yaseyo.onboarding.model.OnboardingStep
import dev.yaseyo.onboarding.model.OnboardingSummary
import dev.yaseyo.onboarding.network.OnboardingApi
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

internal class OnboardingRepositoryImpl(
    private val localDataSource: OnboardingLocalDataSource,
    private val api: OnboardingApi,
) : OnboardingRepository {
    override val draft: Flow<OnboardingDraft> = localDataSource.draft

    override suspend fun saveDraft(draft: OnboardingDraft) = localDataSource.saveDraft(draft)

    override suspend fun clearDraft() = localDataSource.clearDraft()

    override suspend fun getSteps(): Result<List<OnboardingStep>> = runCatching { api.getSteps() }

    override suspend fun getActivityLevels(): Result<List<ActivityLevelOption>> = runCatching { api.getActivityLevels() }

    override suspend fun getGoalSuggestion(
        sex: Sex,
        heightCm: Int,
        dateOfBirth: LocalDate,
        currentWeightKg: Int,
    ): Result<GoalSuggestion> = runCatching { api.getGoalSuggestion(sex, heightCm, dateOfBirth, currentWeightKg) }

    override suspend fun getSummary(
        sex: Sex,
        heightCm: Int,
        dateOfBirth: LocalDate,
        currentWeightKg: Int,
        activityLevel: ActivityLevel,
        dailyDeficitKcal: Int,
    ): Result<OnboardingSummary> =
        runCatching { api.getSummary(sex, heightCm, dateOfBirth, currentWeightKg, activityLevel, dailyDeficitKcal) }
}
