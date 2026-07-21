package dev.yaseyo.onboarding.ui.profilesetup

import dev.yaseyo.onboarding.model.ActivityLevelOption
import dev.yaseyo.onboarding.model.GoalSuggestion
import dev.yaseyo.onboarding.model.OnboardingStep
import dev.yaseyo.onboarding.model.OnboardingSummary
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import kotlinx.datetime.LocalDate

internal data class ProfileSetupUiState(
    val steps: List<OnboardingStep> = emptyList(),
    val isLoadingSteps: Boolean = true,
    val isSubmitting: Boolean = false,
    val currentStepIndex: Int = 0,
    val name: String = "",
    val sex: Sex? = null,
    val dobMonth: Int = 1,
    val dobDay: Int = 1,
    val dobYear: Int = 1990,
    val heightCm: Int = 170,
    val currentWeightKg: Int = 70,
    val activityLevels: List<ActivityLevelOption> = emptyList(),
    val selectedActivityLevel: ActivityLevel? = null,
    val goalSuggestion: GoalSuggestion? = null,
    val isLoadingGoalSuggestion: Boolean = false,
    val targetWeightKg: Int = 70,
    val dailyDeficitKcal: Int = 500,
    val onboardingSummary: OnboardingSummary? = null,
    val isLoadingSummary: Boolean = false,
)

internal fun daysInMonth(
    month: Int,
    year: Int,
): Int =
    when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
        else -> 31
    }

internal fun ProfileSetupUiState.dateOfBirth(): LocalDate =
    LocalDate(
        year = dobYear,
        month = dobMonth,
        day = dobDay.coerceAtMost(daysInMonth(dobMonth, dobYear)),
    )

internal val ProfileSetupUiState.totalSteps: Int
    get() = steps.size.takeIf { it > 0 } ?: 6

internal val ProfileSetupUiState.currentStep: OnboardingStep?
    get() = steps.getOrNull(currentStepIndex)

internal val ProfileSetupUiState.progressFraction: Float
    get() = (currentStepIndex + 1).toFloat() / totalSteps
