package dev.yaseyo.onboarding

import dev.yaseyo.navigation.FeatureNavigation
import org.koin.dsl.module

val onboardingFeatureAndroidModule = module {
    factory<FeatureNavigation> { OnboardingFeatureNavigation() }
}
