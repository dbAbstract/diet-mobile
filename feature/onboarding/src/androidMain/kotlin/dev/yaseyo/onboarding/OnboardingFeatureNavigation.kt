package dev.yaseyo.onboarding

import androidx.navigation3.runtime.EntryProviderScope
import dev.yaseyo.navigation.AppRoute
import dev.yaseyo.navigation.FeatureNavigation
import dev.yaseyo.onboarding.navigation.OnboardingRoutes
import dev.yaseyo.onboarding.ui.AuthScreen

internal class OnboardingFeatureNavigation : FeatureNavigation {
    override val navEntryProvider: EntryProviderScope<AppRoute>.() -> Unit = {
        entry<OnboardingRoutes.Auth> {
            AuthScreen()
        }
    }
}
