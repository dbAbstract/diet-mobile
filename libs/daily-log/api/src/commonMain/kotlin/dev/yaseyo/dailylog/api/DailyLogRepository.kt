package dev.yaseyo.dailylog.api

import kotlinx.coroutines.flow.Flow

interface DailyLogRepository {
    fun getLogForToday(): Flow<DailyLog?>

    suspend fun logFood(
        foodItemId: String,
        quantity: Double,
        mealType: MealType,
    ): Result<Unit>
}
