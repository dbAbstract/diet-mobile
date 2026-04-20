package dev.yaseyo.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.onboarding.domain.OnboardingProfileDraft
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import dev.yaseyo.user.api.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

enum class ProfileSetupStep { PersonalInfo, Goals }

data class ProfileSetupUiState(
    val draft: OnboardingProfileDraft = OnboardingProfileDraft(),
    val step: ProfileSetupStep = ProfileSetupStep.PersonalInfo,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isComplete: Boolean = false,
)

class ProfileSetupPresenter(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileSetupUiState())
    val state: StateFlow<ProfileSetupUiState> = _state

    fun updateName(value: String) = _state.update { it.copy(draft = it.draft.copy(name = value)) }

    fun updateSex(value: Sex) = _state.update { it.copy(draft = it.draft.copy(sex = value)) }

    fun updateHeight(value: Double) = _state.update { it.copy(draft = it.draft.copy(height = value)) }

    fun updateDateOfBirth(value: LocalDate) = _state.update { it.copy(draft = it.draft.copy(dateOfBirth = value)) }

    fun updateActivityLevel(value: ActivityLevel) = _state.update { it.copy(draft = it.draft.copy(activityLevel = value)) }

    fun updateTargetWeightKg(value: Double) = _state.update { it.copy(draft = it.draft.copy(targetWeightKg = value)) }

    fun updateDailyDeficitKcal(value: Double) = _state.update { it.copy(draft = it.draft.copy(dailyDeficitKcal = value)) }

    fun updateTargetProtein(value: Double) = _state.update { it.copy(draft = it.draft.copy(targetProtein = value)) }

    fun updateTargetCarbs(value: Double) = _state.update { it.copy(draft = it.draft.copy(targetCarbs = value)) }

    fun updateTargetFat(value: Double) = _state.update { it.copy(draft = it.draft.copy(targetFat = value)) }

    fun nextStep() {
        _state.update { it.copy(step = ProfileSetupStep.Goals) }
    }

    fun previousStep() {
        _state.update { it.copy(step = ProfileSetupStep.PersonalInfo) }
    }

    fun submit() {
        val draft = _state.value.draft
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            userRepository
                .createUser(
                    name = draft.name,
                    sex = draft.sex ?: return@launch,
                    height = draft.height ?: return@launch,
                    dateOfBirth = draft.dateOfBirth ?: return@launch,
                    activityLevel = draft.activityLevel ?: return@launch,
                    targetWeightKg = draft.targetWeightKg ?: return@launch,
                    dailyDeficitKcal = draft.dailyDeficitKcal ?: return@launch,
                    targetProtein = draft.targetProtein ?: return@launch,
                    targetCarbs = draft.targetCarbs ?: return@launch,
                    targetFat = draft.targetFat ?: return@launch,
                ).fold(
                    onSuccess = { _state.update { it.copy(isLoading = false, isComplete = true) } },
                    onFailure = { e -> _state.update { it.copy(isLoading = false, error = e.message ?: "Profile creation failed") } },
                )
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}
