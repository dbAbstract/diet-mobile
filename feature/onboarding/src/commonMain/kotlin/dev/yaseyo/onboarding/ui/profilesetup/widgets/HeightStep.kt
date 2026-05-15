package dev.yaseyo.onboarding.ui.profilesetup.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

private val heightRange = (140..220).map { it.toString() }

@Composable
internal fun HeightStep(
    heightCm: Int,
    title: String,
    subtitle: String?,
    onHeightChanged: (Int) -> Unit,
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

        Spacer(Modifier.height(32.dp))

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
                items = heightRange,
                selectedIndex = (heightCm - 140).coerceIn(heightRange.indices),
                onIndexChanged = { onHeightChanged(it + 140) },
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "cm",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.contentSecondary,
                modifier = Modifier.padding(end = 32.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HeightStepLightPreview() {
    YaseyoPreview(darkTheme = false) {
        HeightStep(
            heightCm = 175,
            title = "How tall are you?",
            subtitle = "Used to calculate your calorie needs.",
            onHeightChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HeightStepDarkPreview() =
    YaseyoPreview(darkTheme = true) {
        HeightStep(
            heightCm = 175,
            title = "How tall are you?",
            subtitle = "Used to calculate your calorie needs.",
            onHeightChanged = {},
        )
    }
