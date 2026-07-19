package dev.yaseyo.onboarding.ui.profilesetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.coroutines.DispatcherProvider
import dev.yaseyo.navigation.AppRouter
import dev.yaseyo.navigation.Home
import dev.yaseyo.onboarding.model.OnboardingDraft
import dev.yaseyo.onboarding.repository.OnboardingRepository
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import dev.yaseyo.user.api.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ProfileSetupViewModel(
    private val repo: OnboardingRepository,
    private val userRepository: UserRepository,
    private val dispatchers: DispatcherProvider,
    private val router: AppRouter,
) : ViewModel(),
    ProfileSetupEventHandler {
    private val _action = Channel<String>(capacity = Channel.BUFFERED)
    val action = _action.receiveAsFlow()
    private val draftState: StateFlow<OnboardingDraft> = repo.draft
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = OnboardingDraft(),
        )

    private val _uiState = MutableStateFlow(ProfileSetupUiState())

    val uiState: StateFlow<ProfileSetupUiState> = combine(draftState, _uiState) { draft, state ->
        state.copy(
            currentStepIndex = draft.currentStepIndex,
            name = draft.name,
            sex = draft.sex,
            dobDay = draft.dobDay,
            dobMonth = draft.dobMonth,
            dobYear = draft.dobYear,
            heightCm = draft.heightCm,
            currentWeightKg = draft.currentWeightKg,
            selectedActivityLevel = draft.activityLevel,
            targetWeightKg = draft.targetWeightKg,
            dailyDeficitKcal = draft.dailyDeficitKcal,
        )
    }.onStart { loadInitialData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileSetupUiState(),
        )

    private fun loadInitialData() {
        viewModelScope.launch(dispatchers.io) {
            val stepsDeferred = async { repo.getSteps() }
            val levelsDeferred = async { repo.getActivityLevels() }

            stepsDeferred
                .await()
                .onSuccess { steps ->
                    _uiState.update {
                        it.copy(
                            steps = steps,
                            isLoadingSteps = false,
                        )
                    }
                }.onFailure {
                    _uiState.update { it.copy(isLoadingSteps = false) }
                }

            levelsDeferred
                .await()
                .onSuccess { levels -> _uiState.update { it.copy(activityLevels = levels) } }
        }
    }

    override fun onNameChanged(name: String) = save { it.copy(name = name) }

    override fun onSexChanged(sex: Sex) = save { it.copy(sex = sex) }

    override fun onDobMonthChanged(month: Int) = save { it.copy(dobMonth = month) }

    override fun onDobDayChanged(day: Int) = save { it.copy(dobDay = day) }

    override fun onDobYearChanged(year: Int) = save { it.copy(dobYear = year) }

    override fun onHeightChanged(heightCm: Int) = save { it.copy(heightCm = heightCm) }

    override fun onCurrentWeightChanged(weightKg: Int) = save { it.copy(currentWeightKg = weightKg) }

    override fun onActivityLevelSelected(level: ActivityLevel) = save { it.copy(activityLevel = level) }

    override fun onTargetWeightChanged(weightKg: Int) = save { it.copy(targetWeightKg = weightKg) }

    override fun onDeficitChanged(kcal: Int) {
        save { it.copy(dailyDeficitKcal = kcal) }
        fetchSummary(overrideDeficitKcal = kcal)
    }

    override fun onContinue() {
        val state = uiState.value
        val nextIndex = state.currentStepIndex + 1
        if (state.currentStepIndex == 4) fetchGoalSuggestion()
        if (nextIndex < state.totalSteps) {
            save { it.copy(currentStepIndex = nextIndex) }
            if (nextIndex == state.totalSteps - 1) fetchSummary()
        } else {
            submitProfile()
        }
    }

    override fun onBack() {
        val currentIndex = draftState.value.currentStepIndex
        if (currentIndex == 0) {
            router.goBack()
        } else {
            save { it.copy(currentStepIndex = currentIndex - 1) }
        }
    }

    private fun fetchGoalSuggestion() {
        val state = uiState.value
        val sex = state.sex ?: return
        _uiState.update { it.copy(isLoadingGoalSuggestion = true) }
        viewModelScope.launch(dispatchers.io) {
            repo
                .getGoalSuggestion(
                    sex = sex,
                    heightCm = state.heightCm,
                    dateOfBirth = state.dateOfBirth(),
                    currentWeightKg = state.currentWeightKg,
                ).onSuccess { suggestion ->
                    _uiState.update { it.copy(goalSuggestion = suggestion, isLoadingGoalSuggestion = false) }
                    save { it.copy(targetWeightKg = suggestion.suggestedTargetKg) }
                }.onFailure {
                    _uiState.update { it.copy(isLoadingGoalSuggestion = false) }
                }
        }
    }

    private fun fetchSummary(overrideDeficitKcal: Int? = null) {
        val state = uiState.value
        val sex = state.sex ?: return
        val activityLevel = state.selectedActivityLevel ?: return
        _uiState.update { it.copy(isLoadingSummary = true) }
        viewModelScope.launch(dispatchers.io) {
            repo
                .getSummary(
                    sex = sex,
                    heightCm = state.heightCm,
                    dateOfBirth = state.dateOfBirth(),
                    currentWeightKg = state.currentWeightKg,
                    activityLevel = activityLevel,
                    dailyDeficitKcal = overrideDeficitKcal ?: state.dailyDeficitKcal,
                ).onSuccess { summary ->
                    _uiState.update { it.copy(onboardingSummary = summary, isLoadingSummary = false) }
                }.onFailure {
                    _uiState.update { it.copy(isLoadingSummary = false) }
                }
        }
    }

    private fun save(transform: (OnboardingDraft) -> OnboardingDraft) {
        viewModelScope.launch(dispatchers.io) { repo.saveDraft(transform(draftState.value)) }
    }

    private fun submitProfile() {
        val state = uiState.value
        val sex = state.sex ?: return
        val activityLevel = state.selectedActivityLevel ?: return
        val summary = state.onboardingSummary ?: return

        _uiState.update { it.copy(isSubmitting = true) }
        viewModelScope.launch(dispatchers.io) {
            userRepository
                .createUser(
                    name = state.name,
                    sex = sex,
                    height = state.heightCm.toDouble(),
                    dateOfBirth = state.dateOfBirth(),
                    activityLevel = activityLevel,
                    targetWeightKg = state.targetWeightKg.toDouble(),
                    dailyDeficitKcal = state.dailyDeficitKcal.toDouble(),
                    targetProtein = summary.proteinG.toDouble(),
                    targetCarbs = summary.carbsG.toDouble(),
                    targetFat = summary.fatG.toDouble(),
                ).fold(
                    onSuccess = {
                        repo.clearDraft()
                        router.navigateAndClearBackStack(Home)
                    },
                    onFailure = {
                        _uiState.update { it.copy(isSubmitting = false) }
                        _action.send("Something went wrong. Please try again.")
                    },
                )
        }
    }
}
