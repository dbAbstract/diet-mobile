package dev.yaseyo.onboarding.ui.profilesetup.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yaseyo.design.YaseyoPreview
import dev.yaseyo.design.YaseyoTheme

@Composable
internal fun DateOfBirthStep(
    dobMonth: Int,
    dobDay: Int,
    dobYear: Int,
    title: String,
    subtitle: String?,
    onMonthChanged: (Int) -> Unit,
    onDayChanged: (Int) -> Unit,
    onYearChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors
    Column(modifier = modifier) {
        Text(text = title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = colors.contentPrimary)
        if (subtitle != null) {
            Spacer(Modifier.height(6.dp))
            Text(text = subtitle, fontSize = 15.sp, color = colors.contentSecondary)
        }
        Spacer(Modifier.height(24.dp))
        WheelDatePicker(
            month = dobMonth,
            day = dobDay,
            year = dobYear,
            onMonthChanged = onMonthChanged,
            onDayChanged = onDayChanged,
            onYearChanged = onYearChanged,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DateOfBirthStepPreview() {
    YaseyoPreview(darkTheme = false) {
        DateOfBirthStep(
            dobMonth = 6,
            dobDay = 15,
            dobYear = 1990,
            title = "When were you born?",
            subtitle = "Age affects your metabolic rate",
            onMonthChanged = {},
            onDayChanged = {},
            onYearChanged = {},
        )
    }
}
