package dev.yaseyo.navigation

sealed interface NavigationEvent {
    data class GoTo(
        val route: AppRoute,
    ) : NavigationEvent

    data class ClearBackStackAndGoTo(
        val route: AppRoute,
    ) : NavigationEvent

    data object GoBack : NavigationEvent
}
