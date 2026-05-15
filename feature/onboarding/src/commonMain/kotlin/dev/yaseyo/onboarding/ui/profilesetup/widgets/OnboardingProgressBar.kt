package dev.yaseyo.onboarding.ui.profilesetup.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.yaseyo.design.YaseyoPreview
import dev.yaseyo.design.YaseyoTheme

@Composable
internal fun OnboardingProgressBar(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        repeat(totalSteps) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(
                        if (index < currentStep) {
                            colors.accentDefault
                        } else {
                            colors.backgroundSubtle
                        },
                    ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingProgressBarStep1Preview() {
    YaseyoPreview(darkTheme = false) {
        OnboardingProgressBar(currentStep = 1, totalSteps = 7)
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingProgressBarStep4Preview() {
    YaseyoPreview(darkTheme = false) {
        OnboardingProgressBar(currentStep = 4, totalSteps = 7)
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingProgressBarDarkPreview() {
    YaseyoPreview(darkTheme = true) {
        OnboardingProgressBar(currentStep = 5, totalSteps = 7)
    }
}
