package dev.yaseyo.home.ui

import dev.yaseyo.dailylog.api.DailyLog

internal sealed interface HomeUiState {
    val loading: Boolean

    data object Initializing : HomeUiState {
        override val loading: Boolean = true
    }

    data class Content(
        val dailyLog: DailyLog,
        override val loading: Boolean = false,
    ) : HomeUiState

    data object Error : HomeUiState {
        override val loading: Boolean = false
    }
}
