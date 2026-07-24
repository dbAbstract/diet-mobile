package dev.yaseyo.dailylog.impl.network

import kotlinx.datetime.LocalDate

internal interface DailyLogApi {
    suspend fun getLog(date: LocalDate): DailyLogNet

    suspend fun logEntry(
        date: LocalDate,
        request: LogEntryRequestNet,
    )
}
