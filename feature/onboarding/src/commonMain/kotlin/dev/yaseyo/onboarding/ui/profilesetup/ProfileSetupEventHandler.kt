package dev.yaseyo.onboarding.ui.profilesetup

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex

internal interface ProfileSetupEventHandler {
    // Step 1
    fun onNameChanged(name: String)

    // Step 2
    fun onSexChanged(sex: Sex)

    fun onDobMonthChanged(month: Int)

    fun onDobDayChanged(day: Int)

    fun onDobYearChanged(year: Int)

    // Step 3
    fun onHeightChanged(heightCm: Int)

    // Step 4
    fun onCurrentWeightChanged(weightKg: Int)

    // Step 5
    fun onActivityLevelSelected(level: ActivityLevel)

    // Step 6
    fun onTargetWeightChanged(weightKg: Int)

    // Step 7
    fun onDeficitChanged(kcal: Int)

    fun onContinue()

    fun onBack()
}
