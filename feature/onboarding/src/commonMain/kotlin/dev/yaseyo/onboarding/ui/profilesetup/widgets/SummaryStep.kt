package dev.yaseyo.onboarding.ui.profilesetup.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yaseyo.design.YaseyoPreview
import dev.yaseyo.design.YaseyoTheme
import dev.yaseyo.onboarding.model.OnboardingSummary

private val deficitOptions = listOf(
    250 to "Mild",
    500 to "Moderate",
    750 to "Aggressive",
)

@Composable
internal fun SummaryStep(
    summary: OnboardingSummary?,
    isLoading: Boolean,
    dailyDeficitKcal: Int,
    title: String,
    subtitle: String?,
    onDeficitChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors

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

        Text(
            text = "DAILY DEFICIT",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = colors.contentTertiary,
        )

        Spacer(Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            deficitOptions.forEach { (kcal, label) ->
                DeficitChip(
                    label = label,
                    kcal = kcal,
                    selected = dailyDeficitKcal == kcal,
                    onClick = { onDeficitChanged(kcal) },
                    modifier = Modifier.weight(1f),
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.backgroundSubtle)
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = colors.accentDefault)
            }
        } else if (summary != null) {
            SummaryCard(summary = summary)
        }
    }
}

@Composable
private fun DeficitChip(
    label: String,
    kcal: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors
    val bg = if (selected) colors.accentSubtle else colors.backgroundElevated
    val border = if (selected) colors.accentDefault else Color.Transparent

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .border(1.5.dp, border, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.contentPrimary,
        )
        Text(
            text = "$kcal kcal",
            fontSize = 11.sp,
            color = colors.contentSecondary,
        )
    }
}

@Composable
private fun SummaryCard(
    summary: OnboardingSummary,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.backgroundSubtle)
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Text(
            text = "YOUR PLAN",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = colors.contentTertiary,
        )
        Spacer(Modifier.height(12.dp))
        SummaryRow(label = "Daily calories", value = "${summary.dailyCalorieTarget} kcal", highlight = true)
        SummaryRow(label = "Weekly loss", value = "${summary.weeklyLossKg.format1dp()} kg/week")
        SummaryRow(label = "TDEE", value = "${summary.tdee} kcal")
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 10.dp),
            color = colors.borderSubtle,
        )
        Text(
            text = "SUGGESTED MACROS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = colors.contentTertiary,
        )
        Spacer(Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            MacroColumn(label = "Protein", value = "${summary.proteinG}g")
            MacroColumn(label = "Carbs", value = "${summary.carbsG}g")
            MacroColumn(label = "Fat", value = "${summary.fatG}g")
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    highlight: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = label, fontSize = 14.sp, color = colors.contentSecondary)
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = if (highlight) FontWeight.Bold else FontWeight.SemiBold,
            color = if (highlight) colors.accentDefault else colors.contentPrimary,
        )
    }
}

@Composable
private fun MacroColumn(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = colors.contentPrimary)
        Text(text = label, fontSize = 12.sp, color = colors.contentSecondary)
    }
}

private val previewSummary = OnboardingSummary(
    tdee = 2200,
    dailyCalorieTarget = 1700,
    weeklyLossKg = 0.7,
    proteinG = 150,
    carbsG = 170,
    fatG = 55,
)

@Preview(showBackground = true)
@Composable
private fun SummaryStepLoadingPreview() {
    YaseyoPreview(darkTheme = false) {
        SummaryStep(
            summary = null,
            isLoading = true,
            dailyDeficitKcal = 500,
            title = "Here's your plan",
            subtitle = "Based on your profile and goal.",
            onDeficitChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SummaryStepLoadedPreview() {
    YaseyoPreview(darkTheme = false) {
        SummaryStep(
            summary = previewSummary,
            isLoading = false,
            dailyDeficitKcal = 500,
            title = "Here's your plan",
            subtitle = "Based on your profile and goal.",
            onDeficitChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SummaryStepDarkPreview() =
    YaseyoPreview(darkTheme = true) {
        SummaryStep(
            summary = previewSummary,
            isLoading = false,
            dailyDeficitKcal = 750,
            title = "Here's your plan",
            subtitle = "Based on your profile and goal.",
            onDeficitChanged = {},
        )
    }
