package dev.yaseyo.onboarding.ui.profilesetup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yaseyo.design.YaseyoTheme

@Composable
internal fun OnboardingProgressBar(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors
    val percentage = ((currentStep.toFloat() / totalSteps) * 100).toInt()

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Step $currentStep of $totalSteps",
                color = Color(colors.accentDefault),
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "$percentage%",
                color = Color(colors.contentSecondary),
                fontSize = 13.sp,
            )
        }
        Spacer(Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { currentStep.toFloat() / totalSteps },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = Color(colors.accentDefault),
            trackColor = Color(colors.backgroundSubtle),
            strokeCap = StrokeCap.Round,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingProgressBarStep1Preview() {
    YaseyoTheme(darkTheme = false) {
        OnboardingProgressBar(currentStep = 1, totalSteps = 6)
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingProgressBarStep4Preview() {
    YaseyoTheme(darkTheme = false) {
        OnboardingProgressBar(currentStep = 4, totalSteps = 6)
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingProgressBarDarkPreview() {
    YaseyoTheme(darkTheme = true) {
        OnboardingProgressBar(currentStep = 5, totalSteps = 6)
    }
}
