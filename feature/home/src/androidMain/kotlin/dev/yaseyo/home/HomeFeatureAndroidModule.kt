package dev.yaseyo.home

import dev.yaseyo.navigation.FeatureNavigation
import org.koin.dsl.module

val homeFeatureAndroidModule = module {
    factory<FeatureNavigation> { HomeFeatureNavigation() }
}
