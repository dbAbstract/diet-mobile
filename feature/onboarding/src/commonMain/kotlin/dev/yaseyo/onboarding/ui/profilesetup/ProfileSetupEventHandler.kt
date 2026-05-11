package dev.yaseyo.onboarding.ui.profilesetup

internal interface ProfileSetupEventHandler {
    fun onNameChanged(name: String)

    fun onContinue()

    fun onBack()
}
