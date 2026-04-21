package dev.yaseyo.onboarding.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yaseyo.coroutines.DispatcherProvider
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import dev.yaseyo.user.api.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class ProfileSetupViewModel(
    private val userRepository: UserRepository,
    private val dispatchers: DispatcherProvider,
) : ViewModel(),
    ProfileSetupEventHandler {
    private val _state = MutableStateFlow(ProfileSetupUiState())
    val state: StateFlow<ProfileSetupUiState> = _state

    private val _actions = Channel<ProfileSetupUiAction>(Channel.BUFFERED)
    val actions: Flow<ProfileSetupUiAction> = _actions.receiveAsFlow()

    override fun updateName(value: String) = _state.update { it.copy(draft = it.draft.copy(name = value)) }

    override fun updateSex(value: Sex) = _state.update { it.copy(draft = it.draft.copy(sex = value)) }

    override fun updateHeight(value: Double) = _state.update { it.copy(draft = it.draft.copy(height = value)) }

    override fun updateDateOfBirth(value: LocalDate) = _state.update { it.copy(draft = it.draft.copy(dateOfBirth = value)) }

    override fun updateActivityLevel(value: ActivityLevel) = _state.update { it.copy(draft = it.draft.copy(activityLevel = value)) }

    override fun updateTargetWeightKg(value: Double) = _state.update { it.copy(draft = it.draft.copy(targetWeightKg = value)) }

    override fun updateDailyDeficitKcal(value: Double) = _state.update { it.copy(draft = it.draft.copy(dailyDeficitKcal = value)) }

    override fun updateTargetProtein(value: Double) = _state.update { it.copy(draft = it.draft.copy(targetProtein = value)) }

    override fun updateTargetCarbs(value: Double) = _state.update { it.copy(draft = it.draft.copy(targetCarbs = value)) }

    override fun updateTargetFat(value: Double) = _state.update { it.copy(draft = it.draft.copy(targetFat = value)) }

    override fun nextStep() = _state.update { it.copy(step = ProfileSetupStep.Goals) }

    override fun previousStep() = _state.update { it.copy(step = ProfileSetupStep.PersonalInfo) }

    override fun submit() {
        val draft = _state.value.draft
        viewModelScope.launch(dispatchers.io) {
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
                    onSuccess = {
                        _state.update { it.copy(isLoading = false) }
                        _actions.send(ProfileSetupUiAction.NavigateToHome)
                    },
                    onFailure = { e ->
                        _state.update { it.copy(isLoading = false, error = e.message ?: "Profile creation failed") }
                    },
                )
        }
    }

    override fun clearError() = _state.update { it.copy(error = null) }
}
