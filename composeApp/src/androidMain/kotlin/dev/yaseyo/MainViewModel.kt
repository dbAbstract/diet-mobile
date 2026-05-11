package dev.yaseyo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.navigation.AppRoute
import dev.yaseyo.onboarding.ResolveStartDestinationUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val resolveStartDestinationUseCase: ResolveStartDestinationUseCase,
) : ViewModel() {
    val startDestination: StateFlow<AppRoute?> = flow {
        emit(resolveStartDestinationUseCase.execute())
    }.stateIn(
        scope = viewModelScope,
        initialValue = null,
        started = SharingStarted.Eagerly,
    )
}
