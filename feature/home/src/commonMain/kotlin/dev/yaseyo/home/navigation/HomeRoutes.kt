package dev.yaseyo.home.navigation

import dev.yaseyo.navigation.AppRoute

sealed interface HomeRoutes : AppRoute {
    data object Home : HomeRoutes
}
