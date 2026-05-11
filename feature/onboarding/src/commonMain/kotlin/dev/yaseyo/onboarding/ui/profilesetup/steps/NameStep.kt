package dev.yaseyo.onboarding.ui.profilesetup.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yaseyo.design.YaseyoTheme

@Composable
internal fun NameStep(
    name: String,
    title: String,
    subtitle: String?,
    onNameChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors

    Column(modifier = modifier) {
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(colors.contentPrimary),
            lineHeight = 34.sp,
        )

        if (subtitle != null) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = subtitle,
                fontSize = 15.sp,
                color = Color(colors.contentSecondary),
            )
        }

        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = name,
            onValueChange = onNameChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Your name",
                    color = Color(colors.contentTertiary),
                    fontSize = 17.sp,
                )
            },
            textStyle = TextStyle(
                fontSize = 17.sp,
                color = Color(colors.contentPrimary),
            ),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(colors.borderDefault),
                focusedBorderColor = Color(colors.accentDefault),
                unfocusedTextColor = Color(colors.contentPrimary),
                focusedTextColor = Color(colors.contentPrimary),
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NameStepEmptyLightPreview() {
    YaseyoTheme(darkTheme = false) {
        NameStep(
            name = "",
            title = "What should we call you?",
            subtitle = "We'll use this to personalize your experience.",
            onNameChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NameStepFilledPreview() {
    YaseyoTheme(darkTheme = false) {
        NameStep(
            name = "Taki",
            title = "What should we call you?",
            subtitle = "We'll use this to personalize your experience.",
            onNameChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NameStepDarkPreview() {
    YaseyoTheme(darkTheme = true) {
        NameStep(
            name = "",
            title = "What should we call you?",
            subtitle = "We'll use this to personalize your experience.",
            onNameChanged = {},
        )
    }
}
