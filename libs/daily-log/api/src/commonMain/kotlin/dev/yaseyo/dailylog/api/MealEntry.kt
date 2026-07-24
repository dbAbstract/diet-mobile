package dev.yaseyo.dailylog.api

import kotlin.time.Instant

data class MealEntry(
    val id: String,
    val dailyLogId: String,
    val mealType: MealType,
    val quantity: Double,
    val notes: String?,
    val foodItemId: String?,
    val recipeId: String?,
    val kcal: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val fiber: Double,
    val loggedAt: Instant,
    val createdAt: Instant,
    val updatedAt: Instant,
)
