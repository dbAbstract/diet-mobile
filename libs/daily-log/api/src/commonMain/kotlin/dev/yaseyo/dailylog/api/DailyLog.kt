package dev.yaseyo.dailylog.api

import dev.yaseyo.domain.models.MacroTarget
import dev.yaseyo.domain.models.Macros
import kotlinx.datetime.LocalDate

data class DailyLog(
    val date: LocalDate,
    val totals: Macros,
    val targets: MacroTarget,
    val activityKcal: Double,
    val mealEntries: List<MealEntry>,
)
