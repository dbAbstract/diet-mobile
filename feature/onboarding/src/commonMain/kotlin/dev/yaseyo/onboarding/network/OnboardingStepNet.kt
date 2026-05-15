package dev.yaseyo.onboarding.network

import kotlinx.serialization.Serializable

@Serializable
internal data class OnboardingStepNet(
    val key: String,
    val type: String,
    val title: String,
    val subtitle: String? = null,
)
