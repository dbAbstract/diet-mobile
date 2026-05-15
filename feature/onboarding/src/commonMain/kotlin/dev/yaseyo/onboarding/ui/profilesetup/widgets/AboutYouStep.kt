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
import dev.yaseyo.design.YaseyoTheme
import dev.yaseyo.user.api.Sex

@Composable
internal fun AboutYouStep(
    sex: Sex?,
    dobMonth: Int,
    dobDay: Int,
    dobYear: Int,
    title: String,
    subtitle: String?,
    onSexChanged: (Sex) -> Unit,
    onMonthChanged: (Int) -> Unit,
    onDayChanged: (Int) -> Unit,
    onYearChanged: (Int) -> Unit,
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

        SectionLabel(text = "SELECT BIOLOGICAL SEX")

        Spacer(Modifier.height(12.dp))

        SexSelectionRow(selected = sex, onSelected = onSexChanged)

        Spacer(Modifier.height(28.dp))

        SectionLabel(text = "DATE OF BIRTH")

        Spacer(Modifier.height(12.dp))

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

@Composable
private fun SectionLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors
    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        color = colors.contentTertiary,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun AboutYouStepNoneSelectedPreview() {
    YaseyoTheme(darkTheme = false) {
        AboutYouStep(
            sex = null,
            dobMonth = 2,
            dobDay = 14,
            dobYear = 1994,
            title = "Tell us about yourself",
            subtitle = "This helps us calculate your calorie needs accurately.",
            onSexChanged = {},
            onMonthChanged = {},
            onDayChanged = {},
            onYearChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutYouStepFemalePreview() {
    YaseyoTheme(darkTheme = false) {
        AboutYouStep(
            sex = Sex.FEMALE,
            dobMonth = 2,
            dobDay = 14,
            dobYear = 1994,
            title = "Tell us about yourself",
            subtitle = "This helps us calculate your calorie needs accurately.",
            onSexChanged = {},
            onMonthChanged = {},
            onDayChanged = {},
            onYearChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutYouStepDarkPreview() {
    YaseyoTheme(darkTheme = true) {
        AboutYouStep(
            sex = Sex.MALE,
            dobMonth = 6,
            dobDay = 1,
            dobYear = 1990,
            title = "Tell us about yourself",
            subtitle = "This helps us calculate your calorie needs accurately.",
            onSexChanged = {},
            onMonthChanged = {},
            onDayChanged = {},
            onYearChanged = {},
        )
    }
}
