package dev.yaseyo.dailylog.api

import dev.yaseyo.domain.models.Macros
import kotlinx.datetime.LocalDate

data class DailyLog(
    val date: LocalDate,
    val totals: Macros,
    val targets: Macros,
    val activity: Long,
)
