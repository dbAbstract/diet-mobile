package dev.yaseyo.onboarding

import dev.yaseyo.design.createYaseyoUiViewController
import dev.yaseyo.onboarding.ui.auth.AuthLandingScreen
import dev.yaseyo.onboarding.ui.auth.SignInSheetScreen
import dev.yaseyo.onboarding.ui.auth.SignUpSheetScreen
import dev.yaseyo.onboarding.ui.profilesetup.ProfileSetupScreen

fun authLandingScreenViewController() = createYaseyoUiViewController { AuthLandingScreen() }

fun signInScreenViewController() = createYaseyoUiViewController { SignInSheetScreen() }

fun signUpScreenViewController() = createYaseyoUiViewController { SignUpSheetScreen() }

fun profileSetupScreenViewController() = createYaseyoUiViewController { ProfileSetupScreen() }
