package dev.yaseyo.onboarding.datasource

import dev.yaseyo.onboarding.model.OnboardingDraft
import kotlinx.coroutines.flow.Flow

internal interface OnboardingLocalDataSource {
    val draft: Flow<OnboardingDraft>

    suspend fun saveDraft(draft: OnboardingDraft)

    suspend fun clearDraft()
}
