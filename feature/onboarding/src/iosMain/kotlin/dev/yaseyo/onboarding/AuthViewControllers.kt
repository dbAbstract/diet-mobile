package dev.yaseyo.onboarding

import androidx.compose.ui.window.ComposeUIViewController
import dev.yaseyo.onboarding.ui.AuthScreen

fun authScreenViewController() = ComposeUIViewController { AuthScreen() }
