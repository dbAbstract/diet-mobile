package dev.yaseyo.navigation

import androidx.navigation3.runtime.EntryProviderScope

interface FeatureNavigation {
    val navEntryProvider: EntryProviderScope<AppRoute>.() -> Unit
}
