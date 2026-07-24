package dev.yaseyo.home

import androidx.navigation3.runtime.EntryProviderScope
import dev.yaseyo.home.navigation.HomeRoutes
import dev.yaseyo.home.ui.HomeScreen
import dev.yaseyo.navigation.AppRoute
import dev.yaseyo.navigation.FeatureNavigation

internal class HomeFeatureNavigation : FeatureNavigation {
    override val navEntryProvider: EntryProviderScope<AppRoute>.() -> Unit = {
        entry<HomeRoutes.Home> {
            HomeScreen()
        }
    }
}
