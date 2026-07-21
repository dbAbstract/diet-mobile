package dev.yaseyo.dailylog.api

interface DailyLogRepository {
    suspend fun getLogForToday(): Result<DailyLog>
}
