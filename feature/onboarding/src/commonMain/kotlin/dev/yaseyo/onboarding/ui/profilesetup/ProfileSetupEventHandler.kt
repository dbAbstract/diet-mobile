package dev.yaseyo.onboarding.ui.profilesetup

import dev.yaseyo.user.api.Sex

internal interface ProfileSetupEventHandler {
    // Step 1
    fun onNameChanged(name: String)

    // Step 2
    fun onSexChanged(sex: Sex)

    fun onDobMonthChanged(month: Int)

    fun onDobDayChanged(day: Int)

    fun onDobYearChanged(year: Int)

    fun onContinue()

    fun onBack()
}
