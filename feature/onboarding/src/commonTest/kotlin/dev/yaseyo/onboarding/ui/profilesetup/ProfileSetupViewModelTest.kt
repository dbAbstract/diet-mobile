@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.yaseyo.onboarding.ui.profilesetup

import app.cash.turbine.test
import dev.yaseyo.coroutines.testing.TestDispatcherProvider
import dev.yaseyo.navigation.AppRouter
import dev.yaseyo.onboarding.fake.FakeOnboardingApi
import dev.yaseyo.onboarding.fake.FakeOnboardingRepository
import dev.yaseyo.onboarding.model.OnboardingDraft
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ProfileSetupViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val dispatchers = TestDispatcherProvider(testDispatcher)

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun viewModel(repo: FakeOnboardingRepository = FakeOnboardingRepository()) =
        ProfileSetupViewModel(repo = repo, dispatchers = dispatchers, router = AppRouter())

    // --- draft restoration ---

    @Test
    fun `uiState reflects persisted draft on start`() =
        runTest {
            val draft = OnboardingDraft(name = "Alex", heightCm = 182, sex = Sex.Female)
            val vm = viewModel(repo = FakeOnboardingRepository(initialDraft = draft))
            vm.uiState.test {
                awaitItem()
                advanceUntilIdle()
                val state = expectMostRecentItem()
                assertEquals("Alex", state.name)
                assertEquals(182, state.heightCm)
                assertEquals(Sex.Female, state.sex)
                cancelAndIgnoreRemainingEvents()
            }
        }

    // --- initial data loading ---

    @Test
    fun `loadInitialData populates steps and clears loading flag`() =
        runTest {
            val vm = viewModel()
            vm.uiState.test {
                awaitItem()
                advanceUntilIdle()
                val state = expectMostRecentItem()
                assertEquals(FakeOnboardingApi.DEFAULT_STEPS, state.steps)
                assertFalse(state.isLoadingSteps)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `loadInitialData populates activity levels`() =
        runTest {
            val vm = viewModel()
            vm.uiState.test {
                awaitItem()
                advanceUntilIdle()
                assertEquals(FakeOnboardingApi.DEFAULT_ACTIVITY_LEVELS, expectMostRecentItem().activityLevels)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `loadInitialData clears loading flag on steps failure`() =
        runTest {
            val repo = FakeOnboardingRepository(stepsResult = Result.failure(RuntimeException("error")))
            val vm = viewModel(repo = repo)
            vm.uiState.test {
                awaitItem()
                advanceUntilIdle()
                val state = expectMostRecentItem()
                assertFalse(state.isLoadingSteps)
                assertTrue(state.steps.isEmpty())
                cancelAndIgnoreRemainingEvents()
            }
        }

    // --- event handlers save to repo ---

    @Test
    fun `onNameChanged saves name to repo`() =
        runTest {
            val repo = FakeOnboardingRepository()
            val vm = viewModel(repo = repo)
            vm.onNameChanged("Alex")
            advanceUntilIdle()
            assertEquals("Alex", repo.savedDrafts.last().name)
        }

    @Test
    fun `onSexChanged saves sex to repo`() =
        runTest {
            val repo = FakeOnboardingRepository()
            val vm = viewModel(repo = repo)
            vm.onSexChanged(Sex.Female)
            advanceUntilIdle()
            assertEquals(Sex.Female, repo.savedDrafts.last().sex)
        }

    @Test
    fun `onHeightChanged saves height to repo`() =
        runTest {
            val repo = FakeOnboardingRepository()
            val vm = viewModel(repo = repo)
            vm.onHeightChanged(175)
            advanceUntilIdle()
            assertEquals(175, repo.savedDrafts.last().heightCm)
        }

    @Test
    fun `onActivityLevelSelected saves activity level to repo`() =
        runTest {
            val repo = FakeOnboardingRepository()
            val vm = viewModel(repo = repo)
            vm.onActivityLevelSelected(ActivityLevel.LightlyActive)
            advanceUntilIdle()
            assertEquals(ActivityLevel.LightlyActive, repo.savedDrafts.last().activityLevel)
        }

    // --- uiState reflects saved draft ---

    @Test
    fun `uiState updates when draft is saved`() =
        runTest {
            val vm = viewModel()
            vm.uiState.test {
                awaitItem()
                advanceUntilIdle()
                expectMostRecentItem()
                vm.onNameChanged("Sam")
                advanceUntilIdle()
                assertEquals("Sam", expectMostRecentItem().name)
                cancelAndIgnoreRemainingEvents()
            }
        }

    // --- navigation ---

    @Test
    fun `onContinue advances step index`() =
        runTest {
            val repo = FakeOnboardingRepository()
            val vm = viewModel(repo = repo)
            vm.onContinue()
            advanceUntilIdle()
            assertEquals(1, repo.savedDrafts.last().currentStepIndex)
        }

    @Test
    fun `onBack decrements step index when not at first step`() =
        runTest {
            val repo = FakeOnboardingRepository(initialDraft = OnboardingDraft(currentStepIndex = 2))
            val vm = viewModel(repo = repo)
            advanceUntilIdle() // let draftState collect currentStepIndex = 2 from repo
            vm.onBack()
            advanceUntilIdle()
            assertEquals(1, repo.savedDrafts.last().currentStepIndex)
        }

    @Test
    fun `onBack does not save draft when already at first step`() =
        runTest {
            val repo = FakeOnboardingRepository(initialDraft = OnboardingDraft(currentStepIndex = 0))
            val vm = viewModel(repo = repo)
            vm.onBack()
            advanceUntilIdle()
            assertTrue(repo.savedDrafts.isEmpty())
        }

    // --- goal suggestion ---

    @Test
    fun `fetchGoalSuggestion updates uiState on success`() =
        runTest {
            val repo = FakeOnboardingRepository(
                initialDraft = OnboardingDraft(
                    currentStepIndex = 4,
                    sex = Sex.Male,
                    heightCm = 178,
                    currentWeightKg = 80,
                ),
            )
            val vm = viewModel(repo = repo)
            vm.uiState.test {
                awaitItem()
                advanceUntilIdle()
                expectMostRecentItem()
                vm.onContinue()
                advanceUntilIdle()
                val state = expectMostRecentItem()
                assertNotNull(state.goalSuggestion)
                assertFalse(state.isLoadingGoalSuggestion)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `fetchGoalSuggestion clears loading on failure`() =
        runTest {
            val repo = FakeOnboardingRepository(
                initialDraft = OnboardingDraft(currentStepIndex = 4, sex = Sex.Male),
                goalSuggestionResult = Result.failure(RuntimeException("error")),
            )
            val vm = viewModel(repo = repo)
            vm.uiState.test {
                awaitItem()
                advanceUntilIdle()
                expectMostRecentItem()
                vm.onContinue()
                advanceUntilIdle()
                val state = expectMostRecentItem()
                assertNull(state.goalSuggestion)
                assertFalse(state.isLoadingGoalSuggestion)
                cancelAndIgnoreRemainingEvents()
            }
        }

    // --- summary ---

    @Test
    fun `onDeficitChanged triggers summary fetch`() =
        runTest {
            val repo = FakeOnboardingRepository(
                initialDraft = OnboardingDraft(
                    sex = Sex.Female,
                    heightCm = 165,
                    currentWeightKg = 65,
                    activityLevel = ActivityLevel.LightlyActive,
                ),
            )
            val vm = viewModel(repo = repo)
            vm.uiState.test {
                awaitItem()
                advanceUntilIdle()
                expectMostRecentItem()
                vm.onDeficitChanged(500)
                advanceUntilIdle()
                val state = expectMostRecentItem()
                assertNotNull(state.onboardingSummary)
                assertFalse(state.isLoadingSummary)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `fetchSummary is skipped when sex is missing`() =
        runTest {
            val repo = FakeOnboardingRepository(initialDraft = OnboardingDraft(sex = null))
            val vm = viewModel(repo = repo)
            vm.uiState.test {
                awaitItem()
                advanceUntilIdle()
                expectMostRecentItem()
                vm.onDeficitChanged(500)
                advanceUntilIdle()
                assertNull(expectMostRecentItem().onboardingSummary)
                cancelAndIgnoreRemainingEvents()
            }
        }
}
