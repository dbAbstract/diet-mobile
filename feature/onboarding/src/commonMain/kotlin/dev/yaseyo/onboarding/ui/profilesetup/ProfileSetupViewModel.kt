package dev.yaseyo.onboarding.ui.profilesetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.navigation.AppRouter
import dev.yaseyo.onboarding.model.OnboardingStep
import dev.yaseyo.onboarding.network.OnboardingApi
import dev.yaseyo.user.api.Sex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ProfileSetupViewModel(
    private val onboardingApi: OnboardingApi,
    private val router: AppRouter,
) : ViewModel(),
    ProfileSetupEventHandler {
    private val _uiState = MutableStateFlow(ProfileSetupUiState())
    val uiState: StateFlow<ProfileSetupUiState> = _uiState.asStateFlow()

    init {
        fetchSteps()
    }

    private fun fetchSteps() {
        viewModelScope.launch {
            runCatching { onboardingApi.getSteps() }
                .onSuccess { nets ->
                    _uiState.update { state ->
                        state.copy(
                            steps = nets.map { OnboardingStep(key = it.key, title = it.title, subtitle = it.subtitle) },
                            isLoadingSteps = false,
                        )
                    }
                }.onFailure {
                    _uiState.update { it.copy(isLoadingSteps = false) }
                }
        }
    }

    override fun onNameChanged(name: String) = _uiState.update { it.copy(name = name) }

    override fun onSexChanged(sex: Sex) = _uiState.update { it.copy(sex = sex) }

    override fun onDobMonthChanged(month: Int) = _uiState.update { it.copy(dobMonth = month) }

    override fun onDobDayChanged(day: Int) = _uiState.update { it.copy(dobDay = day) }

    override fun onDobYearChanged(year: Int) = _uiState.update { it.copy(dobYear = year) }

    override fun onHeightChanged(heightCm: Int) = _uiState.update { it.copy(heightCm = heightCm) }

    override fun onContinue() {
        val state = _uiState.value
        val nextIndex = state.currentStepIndex + 1
        if (nextIndex < state.totalSteps) {
            _uiState.update { it.copy(currentStepIndex = nextIndex) }
        } else {
            submitProfile()
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
