package dev.yaseyo.onboarding.api.navigation

import dev.yaseyo.navigation.AppRoute

sealed interface OnboardingRoutes : AppRoute {
    data object Auth : OnboardingRoutes

    data object ProfileSetup : OnboardingRoutes
}
