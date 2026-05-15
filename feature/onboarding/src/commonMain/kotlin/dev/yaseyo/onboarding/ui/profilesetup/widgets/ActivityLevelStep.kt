package dev.yaseyo.onboarding.ui.profilesetup.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yaseyo.design.YaseyoPreview
import dev.yaseyo.design.YaseyoTheme
import dev.yaseyo.onboarding.model.ActivityLevelOption
import dev.yaseyo.user.api.ActivityLevel

@Composable
internal fun ActivityLevelStep(
    options: List<ActivityLevelOption>,
    selected: ActivityLevel?,
    title: String,
    subtitle: String?,
    onSelected: (ActivityLevel) -> Unit,
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

        Column(
            verticalArrangement = androidx.compose.foundation.layout.Arrangement
                .spacedBy(12.dp),
        ) {
            options.forEach { option ->
                ActivityLevelCard(
                    option = option,
                    selected = selected == option.level,
                    onClick = { onSelected(option.level) },
                )
            }
        }
    }
}

@Composable
private fun ActivityLevelCard(
    option: ActivityLevelOption,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors
    val borderColor = if (selected) colors.accentDefault else Color.Transparent
    val cardBg = if (selected) colors.accentSubtle else colors.backgroundElevated

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(cardBg)
            .border(width = 1.5.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Column {
            Text(
                text = option.label,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.contentPrimary,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = option.description,
                fontSize = 13.sp,
                color = colors.contentSecondary,
            )
        }
    }
}

private val previewOptions = listOf(
    ActivityLevelOption(
        level = ActivityLevel.Sedentary,
        label = "Sedentary",
        description = "Desk job or mostly sitting throughout the day.",
    ),
    ActivityLevelOption(
        level = ActivityLevel.LightlyActive,
        label = "Lightly Active",
        description = "On your feet for most of the day — retail, teaching, etc.",
    ),
)

@Preview(showBackground = true)
@Composable
private fun ActivityLevelStepNonePreview() {
    YaseyoPreview(darkTheme = false) {
        ActivityLevelStep(
            options = previewOptions,
            selected = null,
            title = "How active is your daily life?",
            subtitle = "This is about your lifestyle, not how often you train.",
            onSelected = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityLevelStepSelectedPreview() {
    YaseyoPreview(darkTheme = false) {
        ActivityLevelStep(
            options = previewOptions,
            selected = ActivityLevel.Sedentary,
            title = "How active is your daily life?",
            subtitle = "This is about your lifestyle, not how often you train.",
            onSelected = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityLevelStepDarkPreview() =
    YaseyoPreview(darkTheme = true) {
        ActivityLevelStep(
            options = previewOptions,
            selected = ActivityLevel.LightlyActive,
            title = "How active is your daily life?",
            subtitle = "This is about your lifestyle, not how often you train.",
            onSelected = {},
        )
    }
