package dev.yaseyo.navigation

import androidx.compose.runtime.mutableStateListOf

class Navigator {
    val backStack = mutableStateListOf<AppRoute>(RootDestination)

    fun goTo(destination: AppRoute) {
        backStack.add(destination)
    }

    fun replaceRootDestination(destination: AppRoute) {
        if (backStack.lastOrNull() == destination) return

        backStack.add(destination)
        backStack.removeAll {
            it != destination
        }
    }

    fun goBack() {
        backStack.removeLastOrNull()
    }
}
