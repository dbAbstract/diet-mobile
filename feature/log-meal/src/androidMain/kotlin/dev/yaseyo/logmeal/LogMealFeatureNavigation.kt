package dev.yaseyo.logmeal

import androidx.navigation3.runtime.EntryProviderScope
import dev.yaseyo.logmeal.navigation.LogMealRoute
import dev.yaseyo.logmeal.ui.LogMealScreen
import dev.yaseyo.navigation.AppRoute
import dev.yaseyo.navigation.FeatureNavigation

internal class LogMealFeatureNavigation : FeatureNavigation {
    override val navEntryProvider: EntryProviderScope<AppRoute>.() -> Unit = {
        entry<LogMealRoute> {
            LogMealScreen()
        }
    }
}
