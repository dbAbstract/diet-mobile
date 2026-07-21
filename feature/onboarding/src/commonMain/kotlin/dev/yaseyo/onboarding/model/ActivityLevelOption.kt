package dev.yaseyo.onboarding.model

import dev.yaseyo.user.api.ActivityLevel

internal data class ActivityLevelOption(
    val level: ActivityLevel,
    val label: String,
    val description: String,
)
