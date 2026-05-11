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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import dev.yaseyo.design.YaseyoTheme
import dev.yaseyo.onboarding.model.OnboardingStep
import dev.yaseyo.onboarding.ui.profilesetup.widgets.NameStep
import dev.yaseyo.onboarding.ui.profilesetup.widgets.OnboardingProgressBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ProfileSetupScreen(viewModel: ProfileSetupViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val navState = rememberNavigationEventState(NavigationEventInfo.None)

    NavigationBackHandler(
        state = navState,
        isBackEnabled = state.currentStepIndex > 0,
    ) {
        viewModel.onBack()
    }

    ProfileSetupContent(state = state, eventHandler = viewModel)
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
            .background(Color(colors.backgroundBase)),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(onClick = eventHandler::onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(colors.contentPrimary),
                    )
                }
                Text(
                    text = "Yaseyo",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(colors.accentDefault),
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
                    containerColor = Color(colors.accentDefault),
                    disabledContainerColor = Color(colors.accentSubtle),
                ),
                enabled = when (state.currentStepIndex) {
                    0 -> state.name.isNotBlank()
                    else -> true
                },
            ) {
                Text(
                    text = "CONTINUE →",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    color = Color(colors.contentOnAccent),
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

    override fun onContinue() {}

    override fun onBack() {}
}

@Preview(showBackground = true)
@Composable
private fun ProfileSetupNameLightPreview() {
    YaseyoTheme(darkTheme = false) {
        ProfileSetupContent(state = previewState, eventHandler = previewHandler)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileSetupNameDarkPreview() {
    YaseyoTheme(darkTheme = true) {
        ProfileSetupContent(state = previewState, eventHandler = previewHandler)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileSetupNameFilledPreview() {
    YaseyoTheme(darkTheme = false) {
        ProfileSetupContent(state = previewState.copy(name = "Taki"), eventHandler = previewHandler)
    }
}
