package dev.yaseyo.onboarding.fake

import dev.yaseyo.onboarding.model.ActivityLevelOption
import dev.yaseyo.onboarding.model.GoalSuggestion
import dev.yaseyo.onboarding.model.OnboardingStep
import dev.yaseyo.onboarding.model.OnboardingSummary
import dev.yaseyo.onboarding.network.OnboardingApi
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import kotlinx.datetime.LocalDate

internal class FakeOnboardingApi(
    private val error: Throwable? = null,
    val steps: List<OnboardingStep> = DEFAULT_STEPS,
    val activityLevels: List<ActivityLevelOption> = DEFAULT_ACTIVITY_LEVELS,
    val goalSuggestion: GoalSuggestion = DEFAULT_GOAL_SUGGESTION,
    val summary: OnboardingSummary = DEFAULT_SUMMARY,
) : OnboardingApi {
    private fun <T> resolve(value: T): T = error?.let { throw it } ?: value

    override suspend fun getSteps() = resolve(steps)

    override suspend fun getActivityLevels() = resolve(activityLevels)

    override suspend fun getGoalSuggestion(
        sex: Sex,
        heightCm: Int,
        dateOfBirth: LocalDate,
        currentWeightKg: Int,
    ) = resolve(goalSuggestion)

    override suspend fun getSummary(
        sex: Sex,
        heightCm: Int,
        dateOfBirth: LocalDate,
        currentWeightKg: Int,
        activityLevel: ActivityLevel,
        dailyDeficitKcal: Int,
    ) = resolve(summary)

    companion object {
        val DEFAULT_STEPS = listOf(
            OnboardingStep(key = "name", title = "Name", subtitle = null),
            OnboardingStep(key = "about_you", title = "About you", subtitle = null),
        )
        val DEFAULT_ACTIVITY_LEVELS = listOf(
            ActivityLevelOption(level = ActivityLevel.Sedentary, label = "Sedentary", description = "Mostly sitting"),
            ActivityLevelOption(level = ActivityLevel.LightlyActive, label = "Lightly Active", description = "Light movement"),
        )
        val DEFAULT_GOAL_SUGGESTION = GoalSuggestion(
            currentBmi = 24.5,
            currentBodyFatPct = null,
            currentBodyFatCategory = null,
            suggestedTargetKg = 70,
        )
        val DEFAULT_SUMMARY = OnboardingSummary(
            tdee = 2200,
            dailyCalorieTarget = 1700,
            weeklyLossKg = 0.5,
            proteinG = 150,
            carbsG = 180,
            fatG = 60,
        )
    }
}
