package dev.yaseyo

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.yaseyo.design.LocalSnackBarHostState
import dev.yaseyo.design.YaseyoTheme
import dev.yaseyo.navigation.AppRoute
import dev.yaseyo.navigation.AppRouter
import dev.yaseyo.navigation.BottomSheetSceneStrategy
import dev.yaseyo.navigation.FeatureNavigation
import dev.yaseyo.navigation.NavigationEvent
import dev.yaseyo.navigation.Navigator
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App(navigator: Navigator) =
    YaseyoTheme {
        val appRouter = koinInject<AppRouter>()
        val allFeatureNavigation = getKoin().getAll<FeatureNavigation>()
        val bottomSheetStrategy = remember { BottomSheetSceneStrategy<AppRoute>() }

        LaunchedEffect(appRouter) {
            appRouter.events.collect { event ->
                when (event) {
                    is NavigationEvent.GoTo -> navigator.goTo(event.route)
                    is NavigationEvent.ClearBackStackAndGoTo -> navigator.clearAndGoTo(event.route)
                    is NavigationEvent.GoBack -> navigator.goBack()
                }
            }
        }

        MaterialTheme {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = LocalSnackBarHostState.current)
                },
            ) { padding ->
                NavDisplay(
                    backStack = navigator.backStack,
                    modifier = Modifier.padding(padding),
                    onBack = { navigator.goBack() },
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(),
                    ),
                    entryProvider = allFeatureNavigation.registerAll(),
                    sceneStrategies = listOf(bottomSheetStrategy),
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
