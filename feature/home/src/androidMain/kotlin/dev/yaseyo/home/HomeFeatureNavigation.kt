package dev.yaseyo.home

import androidx.navigation3.runtime.EntryProviderScope
import dev.yaseyo.home.ui.HomeScreen
import dev.yaseyo.navigation.AppRoute
import dev.yaseyo.navigation.FeatureNavigation
import dev.yaseyo.navigation.Home

internal class HomeFeatureNavigation : FeatureNavigation {
    override val navEntryProvider: EntryProviderScope<AppRoute>.() -> Unit = {
        entry<Home> {
            HomeScreen()
        }
    }
}
