package dev.yaseyo.onboarding.navigation

import dev.yaseyo.navigation.AppRoute

sealed interface OnboardingRoutes : AppRoute {
    data object Auth : OnboardingRoutes

    data object ProfileSetup : OnboardingRoutes
}
