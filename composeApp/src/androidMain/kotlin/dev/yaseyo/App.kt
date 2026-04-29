package dev.yaseyo

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.yaseyo.navigation.AppRoute
import dev.yaseyo.navigation.FeatureNavigation
import dev.yaseyo.navigation.Navigator
import org.koin.compose.getKoin
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App(navigator: Navigator) {
    val allFeatureNavigation = getKoin().getAll<FeatureNavigation>()

    MaterialTheme {
        Scaffold { padding ->
            NavDisplay(
                backStack = navigator.backStack,
                modifier = Modifier.padding(padding),
                onBack = { navigator.goBack() },
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator(),
                ),
                entryProvider = allFeatureNavigation.registerAll(),
            )
        }
    }
}

private fun List<FeatureNavigation>.registerAll(): (AppRoute) -> NavEntry<AppRoute> =
    entryProvider {
        forEach {
            it.navEntryProvider(this)
        }
    }
