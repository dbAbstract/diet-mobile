package dev.yaseyo.onboarding.fake

import dev.yaseyo.onboarding.datasource.OnboardingLocalDataSource
import dev.yaseyo.onboarding.model.OnboardingDraft
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakeOnboardingLocalDataSource(
    initialDraft: OnboardingDraft = OnboardingDraft(),
) : OnboardingLocalDataSource {
    private val _draft = MutableStateFlow(initialDraft)
    override val draft: Flow<OnboardingDraft> = _draft

    override suspend fun saveDraft(draft: OnboardingDraft) {
        _draft.value = draft
    }

    override suspend fun clearDraft() {
        _draft.value = OnboardingDraft()
    }
}
