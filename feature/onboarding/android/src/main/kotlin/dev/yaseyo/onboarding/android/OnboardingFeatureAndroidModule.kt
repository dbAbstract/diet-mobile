package dev.yaseyo.onboarding.android

import dev.yaseyo.navigation.FeatureNavigation
import org.koin.dsl.module

val onboardingFeatureAndroidModule = module {
    factory<FeatureNavigation> { OnboardingFeatureNavigation() }
}
