package dev.yaseyo.dailylog.impl.repository

import dev.yaseyo.dailylog.api.DailyLog
import dev.yaseyo.dailylog.api.DailyLogRepository
import dev.yaseyo.dailylog.impl.network.DailyLogApi
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

internal class DailyLogRepositoryImpl(
    private val logApi: DailyLogApi,
) : DailyLogRepository {
    override suspend fun getLogForToday(): Result<DailyLog> =
        runCatching {
            logApi
                .getLog(
                    date = Clock
                        .System
                        .now()
                        .toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
                        .date,
                ).toDailyLog()
        }
}
