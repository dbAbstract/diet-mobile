package dev.yaseyo.navigation

import androidx.compose.runtime.mutableStateListOf

class Navigator(
    private val finishHandler: FinishHandler,
) {
    val backStack = mutableStateListOf<AppRoute>()

    fun goTo(destination: AppRoute) {
        backStack.add(destination)
    }

    fun clearAndGoTo(destination: AppRoute) {
        backStack.add(destination)
        backStack.removeRange(0, backStack.size - 1)
    }

    fun goBack() {
        if (backStack.size <= 1) {
            finishHandler.finish()
        } else {
            backStack.removeLastOrNull()
        }
    }
}
