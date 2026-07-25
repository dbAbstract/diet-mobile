package dev.yaseyo.home

import dev.yaseyo.navigation.FeatureNavigation
import org.koin.dsl.bind
import org.koin.dsl.module

val homeFeatureAndroidModule = module {
    factory { HomeFeatureNavigation() } bind FeatureNavigation::class
}
