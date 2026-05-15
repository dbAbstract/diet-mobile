@file:OptIn(ExperimentalMaterial3Api::class)

package dev.yaseyo.onboarding

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation3.runtime.EntryProviderScope
import dev.yaseyo.navigation.AppRoute
import dev.yaseyo.navigation.BottomSheetSceneStrategy
import dev.yaseyo.navigation.FeatureNavigation
import dev.yaseyo.onboarding.navigation.OnboardingRoutes
import dev.yaseyo.onboarding.ui.auth.AuthLandingScreen
import dev.yaseyo.onboarding.ui.auth.SignInSheetScreen
import dev.yaseyo.onboarding.ui.auth.SignUpSheetScreen
import dev.yaseyo.onboarding.ui.profilesetup.ProfileSetupScreen

internal class OnboardingFeatureNavigation : FeatureNavigation {
    override val navEntryProvider: EntryProviderScope<AppRoute>.() -> Unit = {
        entry<OnboardingRoutes.Auth.Landing> {
            AuthLandingScreen()
        }
        entry<OnboardingRoutes.ProfileSetup> {
            ProfileSetupScreen()
        }
        entry<OnboardingRoutes.Auth.SignIn>(metadata = BottomSheetSceneStrategy.bottomSheet()) {
            SignInSheetScreen()
        }
        entry<OnboardingRoutes.Auth.SignUp>(metadata = BottomSheetSceneStrategy.bottomSheet()) {
            SignUpSheetScreen()
        }
    }
}
