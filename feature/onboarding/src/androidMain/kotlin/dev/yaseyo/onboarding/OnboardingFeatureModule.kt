package dev.yaseyo.onboarding

import dev.yaseyo.onboarding.api.di.onboardingModule
import dev.yaseyo.onboarding.impl.onboardingFeatureAndroidModule
import org.koin.dsl.module

val onboardingFeatureModule = module {
    includes(onboardingModule, onboardingFeatureAndroidModule)
}
