package dev.yaseyo.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AppRouter {
    private val _events = MutableSharedFlow<NavigationEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<NavigationEvent> = _events.asSharedFlow()

    fun navigate(route: AppRoute) {
        _events.tryEmit(NavigationEvent.GoTo(route))
    }

    fun goBack() {
        _events.tryEmit(NavigationEvent.GoBack)
    }
}
