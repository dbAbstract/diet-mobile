package dev.yaseyo.onboarding.ui.profilesetup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import dev.yaseyo.design.LocalSnackBarHostState
import dev.yaseyo.design.YaseyoPreview
import dev.yaseyo.design.YaseyoTheme
import dev.yaseyo.onboarding.model.OnboardingStep
import dev.yaseyo.onboarding.ui.profilesetup.widgets.AboutYouStep
import dev.yaseyo.onboarding.ui.profilesetup.widgets.ActivityLevelStep
import dev.yaseyo.onboarding.ui.profilesetup.widgets.CurrentWeightStep
import dev.yaseyo.onboarding.ui.profilesetup.widgets.GoalWeightStep
import dev.yaseyo.onboarding.ui.profilesetup.widgets.HeightStep
import dev.yaseyo.onboarding.ui.profilesetup.widgets.NameStep
import dev.yaseyo.onboarding.ui.profilesetup.widgets.OnboardingProgressBar
import dev.yaseyo.onboarding.ui.profilesetup.widgets.SummaryStep
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ProfileSetupScreen(viewModel: ProfileSetupViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val navState = rememberNavigationEventState(NavigationEventInfo.None)
    val snackBarHostState = LocalSnackBarHostState.current

    NavigationBackHandler(
        state = navState,
        isBackEnabled = state.currentStepIndex > 0,
    ) {
        viewModel.onBack()
    }

    ProfileSetupContent(state = state, eventHandler = viewModel)

    LaunchedEffect(viewModel) {
        viewModel.action.collect { snackBarHostState.showSnackbar(it) }
    }
}

@Composable
private fun ProfileSetupContent(
    state: ProfileSetupUiState,
    eventHandler: ProfileSetupEventHandler,
) {
    val colors = YaseyoTheme.colors

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundBase),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                if (state.currentStepIndex > 0) {
                    IconButton(onClick = eventHandler::onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = colors.contentPrimary,
                        )
                    }
                } else {
                    Spacer(Modifier.size(48.dp))
                }
                Text(
                    text = "Yaseyo",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.accentDefault,
                )
                // Balance the row so title stays centered
                Spacer(Modifier.size(48.dp))
            }

            OnboardingProgressBar(
                currentStep = state.currentStepIndex + 1,
                totalSteps = state.totalSteps,
                modifier = Modifier.padding(horizontal = 24.dp),
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp, vertical = 32.dp),
            ) {
                when (state.currentStepIndex) {
                    0 -> NameStep(
                        name = state.name,
                        title = state.currentStep?.title ?: "What should we call you?",
                        subtitle = state.currentStep?.subtitle,
                        onNameChanged = eventHandler::onNameChanged,
                    )
                    1 -> AboutYouStep(
                        sex = state.sex,
                        dobMonth = state.dobMonth,
                        dobDay = state.dobDay,
                        dobYear = state.dobYear,
                        title = state.currentStep?.title ?: "Tell us about yourself",
                        subtitle = state.currentStep?.subtitle,
                        onSexChanged = eventHandler::onSexChanged,
                        onMonthChanged = eventHandler::onDobMonthChanged,
                        onDayChanged = eventHandler::onDobDayChanged,
                        onYearChanged = eventHandler::onDobYearChanged,
                    )
                    2 -> HeightStep(
                        heightCm = state.heightCm,
                        title = state.currentStep?.title ?: "How tall are you?",
                        subtitle = state.currentStep?.subtitle,
                        onHeightChanged = eventHandler::onHeightChanged,
                    )
                    3 -> CurrentWeightStep(
                        weightKg = state.currentWeightKg,
                        title = state.currentStep?.title ?: "What's your current weight?",
                        subtitle = state.currentStep?.subtitle,
                        onWeightChanged = eventHandler::onCurrentWeightChanged,
                    )
                    4 -> ActivityLevelStep(
                        options = state.activityLevels,
                        selected = state.selectedActivityLevel,
                        title = state.currentStep?.title ?: "How active is your daily life?",
                        subtitle = state.currentStep?.subtitle,
                        onSelected = eventHandler::onActivityLevelSelected,
                    )
                    5 -> GoalWeightStep(
                        targetWeightKg = state.targetWeightKg,
                        currentWeightKg = state.currentWeightKg,
                        suggestion = state.goalSuggestion,
                        isLoading = state.isLoadingGoalSuggestion,
                        title = state.currentStep?.title ?: "What's your goal weight?",
                        subtitle = state.currentStep?.subtitle,
                        onTargetWeightChanged = eventHandler::onTargetWeightChanged,
                    )
                    6 -> SummaryStep(
                        summary = state.onboardingSummary,
                        isLoading = state.isLoadingSummary,
                        dailyDeficitKcal = state.dailyDeficitKcal,
                        title = state.currentStep?.title ?: "Here's your plan",
                        subtitle = state.currentStep?.subtitle,
                        onDeficitChanged = eventHandler::onDeficitChanged,
                    )
                }
            }

            Button(
                onClick = eventHandler::onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.accentDefault,
                    disabledContainerColor = colors.accentSubtle,
                ),
                enabled = !state.isSubmitting &&
                    when (state.currentStepIndex) {
                        0 -> state.name.isNotBlank()
                        1 -> state.sex != null
                        4 -> state.selectedActivityLevel != null
                        6 -> state.onboardingSummary != null
                        else -> true
                    },
            ) {
                Text(
                    text = "CONTINUE →",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    color = colors.contentOnAccent,
                )
            }
        }
    }
}

private val previewSteps = listOf(
    OnboardingStep(key = "name", title = "What should we call you?", subtitle = "We'll use this to personalize your experience."),
    OnboardingStep(
        key = "about_you",
        title = "Tell us about yourself",
        subtitle = "This helps us calculate your calorie needs accurately.",
    ),
)

private val previewState = ProfileSetupUiState(
    steps = previewSteps,
    isLoadingSteps = false,
    currentStepIndex = 0,
    name = "",
)

private val previewHandler = object : ProfileSetupEventHandler {
    override fun onNameChanged(name: String) {}

    override fun onSexChanged(sex: dev.yaseyo.user.api.Sex) {}

    override fun onDobMonthChanged(month: Int) {}

    override fun onDobDayChanged(day: Int) {}

    override fun onDobYearChanged(year: Int) {}

    override fun onHeightChanged(heightCm: Int) {}

    override fun onCurrentWeightChanged(weightKg: Int) {}

    override fun onActivityLevelSelected(level: dev.yaseyo.user.api.ActivityLevel) {}

    override fun onTargetWeightChanged(weightKg: Int) {}

    override fun onDeficitChanged(kcal: Int) {}

    override fun onContinue() {}

    override fun onBack() {}
}

@Preview(showBackground = true)
@Composable
private fun ProfileSetupNameLightPreview() {
    YaseyoPreview(darkTheme = false) {
        ProfileSetupContent(state = previewState, eventHandler = previewHandler)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileSetupNameDarkPreview() {
    YaseyoPreview(darkTheme = true) {
        ProfileSetupContent(state = previewState, eventHandler = previewHandler)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileSetupNameFilledPreview() {
    YaseyoPreview(darkTheme = false) {
        ProfileSetupContent(state = previewState.copy(name = "Alex"), eventHandler = previewHandler)
    }
}
