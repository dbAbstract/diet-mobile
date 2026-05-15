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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import dev.yaseyo.user.api.Sex

@Composable
internal fun SexSelectionRow(
    selected: Sex?,
    onSelected: (Sex) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SexCard(
            label = "Male",
            symbol = "♂",
            selected = selected == Sex.Male,
            onClick = { onSelected(Sex.Male) },
            modifier = Modifier.weight(1f),
        )
        SexCard(
            label = "Female",
            symbol = "♀",
            selected = selected == Sex.Female,
            onClick = { onSelected(Sex.Female) },
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun SexCard(
    label: String,
    symbol: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors
    val iconBg = if (selected) colors.accentDefault else colors.backgroundSubtle
    val iconTint = if (selected) colors.contentOnAccent else colors.contentSecondary
    val labelColor = if (selected) colors.accentDefault else colors.contentPrimary
    val borderColor = if (selected) colors.accentDefault else Color.Transparent
    val cardBg = if (selected) colors.accentSubtle else colors.backgroundElevated

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(cardBg)
            .border(width = 1.5.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(iconBg),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = symbol, fontSize = 24.sp, color = iconTint)
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = labelColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SexSelectionNonePreview() {
    YaseyoPreview(darkTheme = false) {
        SexSelectionRow(selected = null, onSelected = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun SexSelectionFemalePreview() {
    YaseyoPreview(darkTheme = false) {
        SexSelectionRow(selected = Sex.Female, onSelected = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun SexSelectionDarkPreview() {
    YaseyoPreview(darkTheme = true) {
        SexSelectionRow(selected = Sex.Male, onSelected = {})
    }
}
