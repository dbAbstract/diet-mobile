package dev.yaseyo.home.ui

internal sealed interface HomeUiState {
    data object Initializing : HomeUiState

    data class Content(
        val progress: Float,
        val currentKcal: Long,
        val activityKcal: Long,
        val baseTargetKcal: Long,
        val macroStatus: MacroStatus,
    ) : HomeUiState {
        data class MacroStatus(
            val currentProtein: Double,
            val targetProtein: Double,
            val proteinProgress: Float,
            val currentCarbs: Double,
            val targetCarbs: Double,
            val carbsProgress: Float,
            val currentFat: Double,
            val targetFat: Double,
            val fatProgress: Float,
        )
    }

    data object Error : HomeUiState
}
