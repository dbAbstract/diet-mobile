package dev.yaseyo.logmeal

import dev.yaseyo.navigation.FeatureNavigation
import org.koin.dsl.bind
import org.koin.dsl.module

val logMealFeatureAndroidModule = module {
    factory { LogMealFeatureNavigation() } bind FeatureNavigation::class
}
