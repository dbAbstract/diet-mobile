package dev.yaseyo.onboarding.fake

import dev.yaseyo.onboarding.model.ActivityLevelOption
import dev.yaseyo.onboarding.model.GoalSuggestion
import dev.yaseyo.onboarding.model.OnboardingDraft
import dev.yaseyo.onboarding.model.OnboardingStep
import dev.yaseyo.onboarding.model.OnboardingSummary
import dev.yaseyo.onboarding.repository.OnboardingRepository
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDate

internal class FakeOnboardingRepository(
    initialDraft: OnboardingDraft = OnboardingDraft(),
    var stepsResult: Result<List<OnboardingStep>> = Result.success(FakeOnboardingApi.DEFAULT_STEPS),
    var activityLevelsResult: Result<List<ActivityLevelOption>> = Result.success(FakeOnboardingApi.DEFAULT_ACTIVITY_LEVELS),
    var goalSuggestionResult: Result<GoalSuggestion> = Result.success(FakeOnboardingApi.DEFAULT_GOAL_SUGGESTION),
    var summaryResult: Result<OnboardingSummary> = Result.success(FakeOnboardingApi.DEFAULT_SUMMARY),
) : OnboardingRepository {
    private val _draft = MutableStateFlow(initialDraft)
    override val draft: Flow<OnboardingDraft> = _draft

    val savedDrafts = mutableListOf<OnboardingDraft>()

    override suspend fun saveDraft(draft: OnboardingDraft) {
        savedDrafts += draft
        _draft.value = draft
    }

    override suspend fun clearDraft() {
        _draft.value = OnboardingDraft()
    }

    override suspend fun getSteps() = stepsResult

    override suspend fun getActivityLevels() = activityLevelsResult

    override suspend fun getGoalSuggestion(
        sex: Sex,
        heightCm: Int,
        dateOfBirth: LocalDate,
        currentWeightKg: Int,
    ) = goalSuggestionResult

    override suspend fun getSummary(
        sex: Sex,
        heightCm: Int,
        dateOfBirth: LocalDate,
        currentWeightKg: Int,
        activityLevel: ActivityLevel,
        dailyDeficitKcal: Int,
    ) = summaryResult
}
