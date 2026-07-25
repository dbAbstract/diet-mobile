package dev.yaseyo.onboarding

import dev.yaseyo.navigation.FeatureNavigation
import org.koin.dsl.bind
import org.koin.dsl.module

val onboardingFeatureAndroidModule = module {
    factory { OnboardingFeatureNavigation() } bind FeatureNavigation::class
}
