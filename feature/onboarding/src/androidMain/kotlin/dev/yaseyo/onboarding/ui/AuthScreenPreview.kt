package dev.yaseyo.onboarding.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.yaseyo.design.YaseyoTheme

@Preview(showBackground = true)
@Composable
private fun AuthScreenLightPreview() {
    YaseyoTheme(darkTheme = false) { AuthScreen() }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenDarkPreview() {
    YaseyoTheme(darkTheme = true) { AuthScreen() }
}
