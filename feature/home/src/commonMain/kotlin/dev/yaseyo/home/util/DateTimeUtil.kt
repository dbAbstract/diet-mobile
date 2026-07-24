package dev.yaseyo.home.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

internal fun now(): LocalDateTime =
    Clock
        .System
        .now()
        .toLocalDateTime(TimeZone.currentSystemDefault())

internal fun currentDayOfWeek(): String =
    now()
        .dayOfWeek.name
        .lowercase()
        .replaceFirstChar { it.uppercase() }
