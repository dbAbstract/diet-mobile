package dev.yaseyo.onboarding.ui.profilesetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.navigation.AppRouter
import dev.yaseyo.onboarding.model.OnboardingStep
import dev.yaseyo.onboarding.network.GoalSuggestionRequestNet
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
    private val router: AppRouter,
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
                .onSuccess { nets ->
                    _uiState.update { state ->
                        state.copy(
                            steps = nets.map {
                                OnboardingStep(
                                    key = it.key,
                                    title = it.title,
                                    subtitle = it.subtitle,
                                )
                            },
                            isLoadingSteps = false,
                        )
                    }
                }.onFailure {
                    _uiState.update { it.copy(isLoadingSteps = false) }
                }

            levelsDeferred.await().onSuccess { nets ->
                _uiState.update { state ->
                    state.copy(
                        activityLevels = nets.mapNotNull { net ->
                            val level = when (net.level) {
                                "SEDENTARY" -> ActivityLevel.Sedentary
                                "LIGHTLY_ACTIVE" -> ActivityLevel.LightlyActive
                                else -> return@mapNotNull null
                            }
                            ActivityLevelOption(
                                level = level,
                                label = net.label,
                                description = net.description,
                            )
                        },
                    )
                }
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

    override fun onContinue() {
        val state = _uiState.value
        val nextIndex = state.currentStepIndex + 1
        if (state.currentStepIndex == 4) {
            fetchGoalSuggestion(state)
        }
        if (nextIndex < state.totalSteps) {
            _uiState.update { it.copy(currentStepIndex = nextIndex) }
        } else {
            submitProfile()
        }
    }

    private fun fetchGoalSuggestion(state: ProfileSetupUiState) {
        val sex = state.sex ?: return
        _uiState.update { it.copy(isLoadingGoalSuggestion = true) }
        viewModelScope.launch {
            runCatching {
                onboardingApi.getGoalSuggestion(
                    GoalSuggestionRequestNet(
                        sex = sex.name.uppercase(),
                        height = state.heightCm.toDouble(),
                        dateOfBirth = state.dateOfBirth().toString(),
                        currentWeightKg = state.currentWeightKg.toDouble(),
                    ),
                )
            }.onSuccess { net ->
                val suggested = net.suggestedTargetKg.toInt()
                _uiState.update {
                    it.copy(
                        goalSuggestion = GoalSuggestion(
                            currentBmi = net.current.bmi,
                            currentBodyFatPct = net.current.bodyFat?.estimatedPct,
                            currentBodyFatCategory = net.current.bodyFat?.category,
                            suggestedTargetKg = suggested,
                        ),
                        targetWeightKg = suggested,
                        isLoadingGoalSuggestion = false,
                    )
                }
            }.onFailure {
                _uiState.update { it.copy(isLoadingGoalSuggestion = false) }
            }
        }
    }

    override fun onBack() {
        val currentIndex = _uiState.value.currentStepIndex
        if (currentIndex == 0) {
            router.goBack()
        } else {
            _uiState.update { it.copy(currentStepIndex = currentIndex - 1) }
        }
    }

    private fun submitProfile() {
        // TODO: POST /user + POST /weight — wired up on the final step
    }
}
