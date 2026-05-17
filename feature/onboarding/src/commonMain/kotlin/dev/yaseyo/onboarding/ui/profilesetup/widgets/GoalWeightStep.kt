package dev.yaseyo.onboarding.ui.profilesetup.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yaseyo.design.YaseyoPreview
import dev.yaseyo.design.YaseyoTheme
import dev.yaseyo.onboarding.model.GoalSuggestion

@Composable
internal fun GoalWeightStep(
    targetWeightKg: Int,
    currentWeightKg: Int,
    suggestion: GoalSuggestion?,
    isLoading: Boolean,
    title: String,
    subtitle: String?,
    onTargetWeightChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors
    val weightRange = (30..currentWeightKg).map { it.toString() }

    Column(modifier = modifier) {
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = colors.contentPrimary,
        )

        if (subtitle != null) {
            Spacer(Modifier.height(6.dp))
            Text(
                text = subtitle,
                fontSize = 15.sp,
                color = colors.contentSecondary,
            )
        }

        Spacer(Modifier.height(24.dp))

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.backgroundSubtle)
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = colors.accentDefault)
            }
        } else if (suggestion != null) {
            CurrentStatsCard(suggestion = suggestion)
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "TARGET WEIGHT",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = colors.contentTertiary,
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(colors.backgroundSubtle)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            WheelPicker(
                items = weightRange,
                selectedIndex = (targetWeightKg - 30).coerceIn(weightRange.indices),
                onIndexChanged = { onTargetWeightChanged(it + 30) },
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "kg",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.contentSecondary,
                modifier = Modifier.padding(end = 32.dp),
            )
        }
    }
}

@Composable
private fun CurrentStatsCard(
    suggestion: GoalSuggestion,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.backgroundSubtle)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = "YOUR CURRENT STATS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = colors.contentTertiary,
        )
        StatRow(label = "BMI", value = suggestion.currentBmi.format1dp())
        if (suggestion.currentBodyFatPct != null) {
            val category = suggestion.currentBodyFatCategory?.let { " · $it" } ?: ""
            StatRow(label = "Est. Body Fat", value = "${suggestion.currentBodyFatPct.format1dp()}%$category")
        }
    }
}

@Composable
private fun StatRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = label, fontSize = 14.sp, color = colors.contentSecondary)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = colors.contentPrimary)
    }
}

private val previewSuggestion = GoalSuggestion(
    currentBmi = 27.4,
    currentBodyFatPct = 22.1,
    currentBodyFatCategory = "Overweight",
    suggestedTargetKg = 72,
)

@Preview(showBackground = true)
@Composable
private fun GoalWeightStepLoadingPreview() {
    YaseyoPreview(darkTheme = false) {
        GoalWeightStep(
            targetWeightKg = 75,
            currentWeightKg = 85,
            suggestion = null,
            isLoading = true,
            title = "What's your goal weight?",
            subtitle = null,
            onTargetWeightChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GoalWeightStepLoadedPreview() {
    YaseyoPreview(darkTheme = false) {
        GoalWeightStep(
            targetWeightKg = 72,
            currentWeightKg = 85,
            suggestion = previewSuggestion,
            isLoading = false,
            title = "What's your goal weight?",
            subtitle = null,
            onTargetWeightChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GoalWeightStepDarkPreview() =
    YaseyoPreview(darkTheme = true) {
        GoalWeightStep(
            targetWeightKg = 72,
            currentWeightKg = 85,
            suggestion = previewSuggestion,
            isLoading = false,
            title = "What's your goal weight?",
            subtitle = null,
            onTargetWeightChanged = {},
        )
    }
