package dev.yaseyo.onboarding.presentation.profile

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import kotlinx.datetime.LocalDate

interface ProfileSetupEventHandler {
    fun updateName(value: String)

    fun updateSex(value: Sex)

    fun updateHeight(value: Double)

    fun updateDateOfBirth(value: LocalDate)

    fun updateActivityLevel(value: ActivityLevel)

    fun updateTargetWeightKg(value: Double)

    fun updateDailyDeficitKcal(value: Double)

    fun updateTargetProtein(value: Double)

    fun updateTargetCarbs(value: Double)

    fun updateTargetFat(value: Double)

    fun nextStep()

    fun previousStep()

    fun submit()

    fun clearError()
}
