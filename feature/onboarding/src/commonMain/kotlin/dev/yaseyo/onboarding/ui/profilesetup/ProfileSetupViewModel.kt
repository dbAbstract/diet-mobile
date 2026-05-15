package dev.yaseyo.onboarding.ui.profilesetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.onboarding.network.OnboardingApi
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ProfileSetupViewModel(
    private val onboardingApi: OnboardingApi,
) : ViewModel(),
    ProfileSetupEventHandler {
    private val _uiState = MutableStateFlow(ProfileSetupUiState())
    val uiState: StateFlow<ProfileSetupUiState> = _uiState
        .onStart { loadInitialData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileSetupUiState(),
        )

    private fun loadInitialData() {
        viewModelScope.launch {
            val stepsDeferred = async { runCatching { onboardingApi.getSteps() } }
            val levelsDeferred = async { runCatching { onboardingApi.getActivityLevels() } }

            stepsDeferred
                .await()
                .onSuccess { steps ->
                    _uiState.update { it.copy(steps = steps, isLoadingSteps = false) }
                }.onFailure {
                    _uiState.update { it.copy(isLoadingSteps = false) }
                }

            levelsDeferred.await().onSuccess { levels ->
                _uiState.update { it.copy(activityLevels = levels) }
            }
        }
    }

    override fun onNameChanged(name: String) = _uiState.update { it.copy(name = name) }

    override fun onSexChanged(sex: Sex) = _uiState.update { it.copy(sex = sex) }

    override fun onDobMonthChanged(month: Int) = _uiState.update { it.copy(dobMonth = month) }

    override fun onDobDayChanged(day: Int) = _uiState.update { it.copy(dobDay = day) }

    override fun onDobYearChanged(year: Int) = _uiState.update { it.copy(dobYear = year) }

    override fun onHeightChanged(heightCm: Int) = _uiState.update { it.copy(heightCm = heightCm) }

    override fun onCurrentWeightChanged(weightKg: Int) = _uiState.update { it.copy(currentWeightKg = weightKg) }

    override fun onActivityLevelSelected(level: ActivityLevel) = _uiState.update { it.copy(selectedActivityLevel = level) }

    override fun onTargetWeightChanged(weightKg: Int) = _uiState.update { it.copy(targetWeightKg = weightKg) }

    override fun onDeficitChanged(kcal: Int) {
        _uiState.update { it.copy(dailyDeficitKcal = kcal) }
        fetchSummary(_uiState.value)
    }

    override fun onContinue() {
        val state = _uiState.value
        val nextIndex = state.currentStepIndex + 1
        if (state.currentStepIndex == 4) fetchGoalSuggestion(state)
        if (nextIndex < state.totalSteps) {
            _uiState.update { it.copy(currentStepIndex = nextIndex) }
            if (nextIndex == state.totalSteps - 1) fetchSummary(_uiState.value)
        } else {
            submitProfile()
        }
    }

    override fun onBack() {
        val currentIndex = _uiState.value.currentStepIndex
        if (currentIndex == 0) {
            // TODO: navigate back via UiAction (Chunk 4)
        } else {
            _uiState.update { it.copy(currentStepIndex = currentIndex - 1) }
        }
    }

    private fun fetchGoalSuggestion(state: ProfileSetupUiState) {
        val sex = state.sex ?: return
        _uiState.update { it.copy(isLoadingGoalSuggestion = true) }
        viewModelScope.launch {
            runCatching {
                onboardingApi.getGoalSuggestion(
                    sex = sex,
                    heightCm = state.heightCm,
                    dateOfBirth = state.dateOfBirth(),
                    currentWeightKg = state.currentWeightKg,
                )
            }.onSuccess { suggestion ->
                _uiState.update {
                    it.copy(
                        goalSuggestion = suggestion,
                        targetWeightKg = suggestion.suggestedTargetKg,
                        isLoadingGoalSuggestion = false,
                    )
                }
            }.onFailure {
                _uiState.update { it.copy(isLoadingGoalSuggestion = false) }
            }
        }
    }

    private fun fetchSummary(state: ProfileSetupUiState) {
        val sex = state.sex ?: return
        val activityLevel = state.selectedActivityLevel ?: return
        _uiState.update { it.copy(isLoadingSummary = true) }
        viewModelScope.launch {
            runCatching {
                onboardingApi.getSummary(
                    sex = sex,
                    heightCm = state.heightCm,
                    dateOfBirth = state.dateOfBirth(),
                    currentWeightKg = state.currentWeightKg,
                    activityLevel = activityLevel,
                    dailyDeficitKcal = state.dailyDeficitKcal,
                )
            }.onSuccess { summary ->
                _uiState.update { it.copy(onboardingSummary = summary, isLoadingSummary = false) }
            }.onFailure {
                _uiState.update { it.copy(isLoadingSummary = false) }
            }
        }
    }

    private fun submitProfile() {
        // TODO: POST /user — wired up in Chunk 4
    }
}
