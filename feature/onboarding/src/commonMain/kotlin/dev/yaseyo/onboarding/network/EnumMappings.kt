package dev.yaseyo.onboarding.network

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex

internal fun Sex.toApiValue(): String =
    when (this) {
        Sex.Male -> "MALE"
        Sex.Female -> "FEMALE"
    }

internal fun ActivityLevel.toApiValue(): String =
    when (this) {
        ActivityLevel.Sedentary -> "SEDENTARY"
        ActivityLevel.LightlyActive -> "LIGHTLY_ACTIVE"
    }
