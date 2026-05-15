package dev.yaseyo.onboarding

import dev.yaseyo.design.createYaseyoUiViewController
import dev.yaseyo.onboarding.ui.auth.AuthLandingScreen
import dev.yaseyo.onboarding.ui.auth.SignInSheetScreen
import dev.yaseyo.onboarding.ui.auth.SignUpSheetScreen

fun authLandingScreenViewController() = createYaseyoUiViewController { AuthLandingScreen() }

fun signInScreenViewController() = createYaseyoUiViewController { SignInSheetScreen() }

fun signUpScreenViewController() = createYaseyoUiViewController { SignUpSheetScreen() }
