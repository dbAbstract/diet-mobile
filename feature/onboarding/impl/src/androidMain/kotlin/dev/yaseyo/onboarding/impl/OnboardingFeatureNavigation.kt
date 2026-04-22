package dev.yaseyo.onboarding.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.EntryProviderScope
import dev.yaseyo.navigation.AppRoute
import dev.yaseyo.navigation.FeatureNavigation
import dev.yaseyo.onboarding.api.navigation.OnboardingRoutes

internal class OnboardingFeatureNavigation : FeatureNavigation {
    override val navEntryProvider: EntryProviderScope<AppRoute>.() -> Unit = {
        entry<OnboardingRoutes.Auth> {
            Box(modifier = Modifier.fillMaxSize().background(Color.Red))
        }
    }
}
