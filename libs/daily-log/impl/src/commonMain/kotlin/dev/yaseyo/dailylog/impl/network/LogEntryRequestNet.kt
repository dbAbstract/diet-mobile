package dev.yaseyo.dailylog.impl.network

import kotlinx.serialization.Serializable

@Serializable
internal data class LogEntryRequestNet(
    val foodItemId: String,
    val quantity: Double,
    val mealType: String,
)
