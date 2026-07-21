package dev.yaseyo.dailylog.impl.network

import dev.yaseyo.dailylog.api.DailyLog
import dev.yaseyo.domain.models.MacroTarget
import dev.yaseyo.domain.models.Macros
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class DailyLogNet(
    val date: String,
    val totals: Macros,
    val activityKcal: Double,
    val targets: MacroTarget,
) {
    fun toDailyLog(): DailyLog =
        DailyLog(
            date = LocalDate.parse(date),
            totals = totals,
            activityKcal = activityKcal,
            targets = targets,
        )
}
