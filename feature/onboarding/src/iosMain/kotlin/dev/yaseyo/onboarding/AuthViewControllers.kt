package dev.yaseyo.onboarding

import dev.yaseyo.design.createYaseyoUiViewController
import dev.yaseyo.onboarding.ui.AuthScreen

fun authScreenViewController() = createYaseyoUiViewController { AuthScreen() }
