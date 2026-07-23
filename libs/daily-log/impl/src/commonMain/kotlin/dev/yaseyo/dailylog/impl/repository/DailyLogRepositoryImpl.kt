package dev.yaseyo.dailylog.impl.repository

import dev.yaseyo.coroutines.DispatcherProvider
import dev.yaseyo.dailylog.api.DailyLog
import dev.yaseyo.dailylog.api.DailyLogRepository
import dev.yaseyo.dailylog.api.MealType
import dev.yaseyo.dailylog.impl.network.DailyLogApi
import dev.yaseyo.dailylog.impl.network.LogEntryRequestNet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

internal class DailyLogRepositoryImpl(
    private val logApi: DailyLogApi,
    private val dispatcherProvider: DispatcherProvider,
) : DailyLogRepository {
    val logForToday: MutableStateFlow<DailyLog?> = MutableStateFlow(null)

    override fun getLogForToday(): Flow<DailyLog?> =
        logForToday.onStart {
            if (logForToday.value == null) {
                fetchAndUpdateLog()
            }
        }

    override suspend fun logFood(
        foodItemId: String,
        quantity: Double,
        mealType: MealType,
    ): Result<Unit> =
        runCatching {
            withContext(dispatcherProvider.io) {
                logApi.logEntry(
                    date = today(),
                    request = LogEntryRequestNet(
                        foodItemId = foodItemId,
                        quantity = quantity,
                        mealType = mealType.name.uppercase(),
                    ),
                )
                fetchAndUpdateLog()
            }
        }

    private suspend fun fetchAndUpdateLog() {
        runCatching {
            withContext(dispatcherProvider.io) {
                logApi.getLog(today()).toDailyLog()
            }
        }.onSuccess { logForToday.value = it }
    }

    private fun today() =
        Clock.System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
}
