package dev.yaseyo.onboarding

import dev.yaseyo.design.createYaseyoUiViewController
import dev.yaseyo.onboarding.ui.auth.AuthLandingScreen

fun authLandingScreenViewController() = createYaseyoUiViewController { AuthLandingScreen() }
