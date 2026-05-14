package dev.yaseyo.onboarding.ui.profilesetup.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.yaseyo.design.YaseyoTheme
import dev.yaseyo.onboarding.ui.profilesetup.daysInMonth

private val months = listOf(
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
)

// Reasonable birth year range: age 13–100
private val years = (1926..2013).map { it.toString() }

@Composable
internal fun WheelDatePicker(
    month: Int,
    day: Int,
    year: Int,
    onMonthChanged: (Int) -> Unit,
    onDayChanged: (Int) -> Unit,
    onYearChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors

    // Day list adjusts to the selected month/year
    val days = remember(month, year) {
        (1..daysInMonth(month, year)).map { it.toString() }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.backgroundSubtle),
    ) {
        WheelPicker(
            items = months,
            selectedIndex = (month - 1).coerceIn(months.indices),
            onIndexChanged = { onMonthChanged(it + 1) },
            modifier = Modifier.weight(2f), // months are wider
        )
        WheelPicker(
            items = days,
            selectedIndex = (day - 1).coerceIn(days.indices),
            onIndexChanged = { onDayChanged(it + 1) },
            modifier = Modifier.weight(1f),
        )
        WheelPicker(
            items = years,
            selectedIndex = (year - 1926).coerceIn(years.indices),
            onIndexChanged = { onYearChanged(it + 1926) },
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WheelDatePickerPreview() {
    YaseyoTheme(darkTheme = false) {
        WheelDatePicker(
            month = 2,
            day = 14,
            year = 1994,
            onMonthChanged = {},
            onDayChanged = {},
            onYearChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WheelDatePickerDarkPreview() {
    YaseyoTheme(darkTheme = true) {
        WheelDatePicker(
            month = 6,
            day = 1,
            year = 1990,
            onMonthChanged = {},
            onDayChanged = {},
            onYearChanged = {},
        )
    }
}
