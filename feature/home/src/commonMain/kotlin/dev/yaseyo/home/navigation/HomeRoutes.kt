package dev.yaseyo.home.navigation

import dev.yaseyo.navigation.AppRoute

internal sealed interface HomeRoutes : AppRoute {
    data object LogMeal : HomeRoutes
}
