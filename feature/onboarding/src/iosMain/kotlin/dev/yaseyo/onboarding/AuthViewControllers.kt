package dev.yaseyo.onboarding

import dev.yaseyo.design.createYaseyoUiViewController
import dev.yaseyo.onboarding.ui.auth.AuthScreen

fun authScreenViewController() = createYaseyoUiViewController { AuthScreen() }
