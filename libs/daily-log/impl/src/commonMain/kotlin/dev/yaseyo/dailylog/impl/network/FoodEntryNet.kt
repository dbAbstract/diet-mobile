package dev.yaseyo.dailylog.impl.network

import dev.yaseyo.dailylog.api.MealEntry
import dev.yaseyo.dailylog.api.MealType
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
internal data class FoodEntryNet(
    val id: String,
    val dailyLogId: String,
    val mealType: String,
    val quantity: Double,
    val notes: String? = null,
    val foodItemId: String? = null,
    val recipeId: String? = null,
    val kcal: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val fiber: Double,
    val loggedAt: String,
    val createdAt: String,
    val updatedAt: String,
)

internal fun FoodEntryNet.toDomain() =
    MealEntry(
        id = id,
        dailyLogId = dailyLogId,
        mealType = mealType.toMealType(),
        quantity = quantity,
        notes = notes,
        foodItemId = foodItemId,
        recipeId = recipeId,
        kcal = kcal,
        protein = protein,
        carbs = carbs,
        fat = fat,
        fiber = fiber,
        loggedAt = Instant.parse(loggedAt),
        createdAt = Instant.parse(createdAt),
        updatedAt = Instant.parse(updatedAt),
    )

private fun String.toMealType() = MealType.valueOf(lowercase().replaceFirstChar { it.uppercase() })
