package dev.yaseyo.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.dailylog.api.DailyLogRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class HomeViewModel(
    dailyLogRepository: DailyLogRepository,
) : ViewModel(),
    HomeEventHandler {
    val uiState: StateFlow<HomeUiState> = dailyLogRepository
        .getLogForToday()
        .map { dailyLogResult ->
            dailyLogResult?.fold(
                onSuccess = {
                    HomeUiState.Content(
                        currentKcal = it.totals.kcal.toLong(),
                        activityKcal = (it.targets.effectiveKcal - it.targets.kcal).toLong(),
                        baseTargetKcal = it.targets.kcal.toLong(),
                        progress = (it.totals.kcal / it.targets.effectiveKcal).toFloat(),
                    )
                },
                onFailure = {
                    HomeUiState.Error
                },
            ) ?: HomeUiState.Initializing
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Initializing,
        )
}
