package dev.yaseyo.home.ui

import dev.yaseyo.dailylog.api.MealEntry

internal sealed interface HomeUiState {
    data object Initializing : HomeUiState

    data class Content(
        val progress: Float,
        val currentKcal: Long,
        val activityKcal: Long,
        val baseTargetKcal: Long,
        val macroStatus: MacroStatus,
        val mealEntries: List<MealEntry>,
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
