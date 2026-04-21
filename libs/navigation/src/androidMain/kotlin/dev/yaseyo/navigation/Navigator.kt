package dev.yaseyo.navigation

import androidx.compose.runtime.mutableStateListOf

class Navigator {
    val backStack = mutableStateListOf<AppRoute>()

    fun goTo(destination: AppRoute) {
        backStack.add(destination)
    }

    fun goBack() {
        backStack.removeLastOrNull()
    }
}
