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
import dev.yaseyo.user.api.Sex

@Composable
internal fun SexStep(
    sex: Sex?,
    title: String,
    subtitle: String?,
    onSexChanged: (Sex) -> Unit,
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
        SexSelectionRow(selected = sex, onSelected = onSexChanged)
    }
}

@Preview(showBackground = true)
@Composable
private fun SexStepNonePreview() {
    YaseyoPreview(darkTheme = false) {
        SexStep(sex = null, title = "Biological sex", subtitle = "Used for accurate calorie calculations", onSexChanged = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun SexStepSelectedPreview() {
    YaseyoPreview(darkTheme = true) {
        SexStep(sex = Sex.Male, title = "Biological sex", subtitle = "Used for accurate calorie calculations", onSexChanged = {})
    }
}
