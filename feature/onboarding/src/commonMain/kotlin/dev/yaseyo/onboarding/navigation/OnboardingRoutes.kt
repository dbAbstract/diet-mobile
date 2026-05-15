package dev.yaseyo.onboarding.navigation

import dev.yaseyo.navigation.AppRootRoute
import dev.yaseyo.navigation.AppRoute
import dev.yaseyo.navigation.ModalRoute

sealed interface OnboardingRoutes : AppRoute {
    data object Auth : OnboardingRoutes {
        data object Landing : OnboardingRoutes, AppRootRoute

        data object SignIn : OnboardingRoutes, ModalRoute

        data object SignUp : OnboardingRoutes, ModalRoute
    }

    data object ProfileSetup : OnboardingRoutes
}
