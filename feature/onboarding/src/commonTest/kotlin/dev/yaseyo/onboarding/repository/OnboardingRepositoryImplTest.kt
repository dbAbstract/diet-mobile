package dev.yaseyo.onboarding.repository

import dev.yaseyo.onboarding.fake.FakeOnboardingApi
import dev.yaseyo.onboarding.fake.FakeOnboardingLocalDataSource
import dev.yaseyo.onboarding.model.OnboardingDraft
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OnboardingRepositoryImplTest {
    private fun repo(
        localDataSource: FakeOnboardingLocalDataSource = FakeOnboardingLocalDataSource(),
        api: FakeOnboardingApi = FakeOnboardingApi(),
    ) = OnboardingRepositoryImpl(localDataSource = localDataSource, api = api)

    // --- draft ---

    @Test
    fun `draft reflects local data source`() =
        runTest {
            val initial = OnboardingDraft(name = "Alex", heightCm = 180)
            val repo = repo(localDataSource = FakeOnboardingLocalDataSource(initial))
            assertEquals(initial, repo.draft.first())
        }

    // --- saveDraft / clearDraft ---

    @Test
    fun `saveDraft persists to local data source`() =
        runTest {
            val source = FakeOnboardingLocalDataSource()
            val updated = OnboardingDraft(name = "Alex", sex = Sex.Male)
            repo(localDataSource = source).saveDraft(updated)
            assertEquals(updated, source.draft.first())
        }

    @Test
    fun `clearDraft resets to defaults`() =
        runTest {
            val source = FakeOnboardingLocalDataSource(OnboardingDraft(name = "Alex"))
            repo(localDataSource = source).clearDraft()
            assertEquals(OnboardingDraft(), source.draft.first())
        }

    // --- getSteps ---

    @Test
    fun `getSteps returns success from api`() =
        runTest {
            val result = repo().getSteps()
            assertTrue(result.isSuccess)
            assertEquals(FakeOnboardingApi.DEFAULT_STEPS, result.getOrThrow())
        }

    @Test
    fun `getSteps returns failure when api throws`() =
        runTest {
            val error = RuntimeException("network error")
            val result = repo(api = FakeOnboardingApi(error = error)).getSteps()
            assertTrue(result.isFailure)
            assertEquals(error, result.exceptionOrNull())
        }

    // --- getActivityLevels ---

    @Test
    fun `getActivityLevels returns success from api`() =
        runTest {
            val result = repo().getActivityLevels()
            assertTrue(result.isSuccess)
            assertEquals(FakeOnboardingApi.DEFAULT_ACTIVITY_LEVELS, result.getOrThrow())
        }

    @Test
    fun `getActivityLevels returns failure when api throws`() =
        runTest {
            val error = RuntimeException("network error")
            val result = repo(api = FakeOnboardingApi(error = error)).getActivityLevels()
            assertTrue(result.isFailure)
        }

    // --- getGoalSuggestion ---

    @Test
    fun `getGoalSuggestion returns success from api`() =
        runTest {
            val result = repo().getGoalSuggestion(
                sex = Sex.Male,
                heightCm = 178,
                dateOfBirth = LocalDate(1990, 6, 15),
                currentWeightKg = 80,
            )
            assertTrue(result.isSuccess)
            assertEquals(FakeOnboardingApi.DEFAULT_GOAL_SUGGESTION, result.getOrThrow())
        }

    @Test
    fun `getGoalSuggestion returns failure when api throws`() =
        runTest {
            val error = RuntimeException("network error")
            val result = repo(api = FakeOnboardingApi(error = error)).getGoalSuggestion(
                sex = Sex.Male,
                heightCm = 178,
                dateOfBirth = LocalDate(1990, 6, 15),
                currentWeightKg = 80,
            )
            assertTrue(result.isFailure)
            assertEquals(error, result.exceptionOrNull())
        }

    // --- getSummary ---

    @Test
    fun `getSummary returns success from api`() =
        runTest {
            val result = repo().getSummary(
                sex = Sex.Female,
                heightCm = 165,
                dateOfBirth = LocalDate(1995, 3, 20),
                currentWeightKg = 65,
                activityLevel = ActivityLevel.LightlyActive,
                dailyDeficitKcal = 500,
            )
            assertTrue(result.isSuccess)
            assertEquals(FakeOnboardingApi.DEFAULT_SUMMARY, result.getOrThrow())
        }

    @Test
    fun `getSummary returns failure when api throws`() =
        runTest {
            val error = RuntimeException("network error")
            val result = repo(api = FakeOnboardingApi(error = error)).getSummary(
                sex = Sex.Female,
                heightCm = 165,
                dateOfBirth = LocalDate(1995, 3, 20),
                currentWeightKg = 65,
                activityLevel = ActivityLevel.LightlyActive,
                dailyDeficitKcal = 500,
            )
            assertTrue(result.isFailure)
        }
}
