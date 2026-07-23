package dev.yaseyo.home.ui

internal sealed interface HomeUiState {
    data object Initializing : HomeUiState

    data class Content(
        val progress: Float,
        val currentKcal: Long,
        val activityKcal: Long,
        val baseTargetKcal: Long,
    ) : HomeUiState

    data object Error : HomeUiState
}
