package dev.yaseyo.design

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun YaseyoPreview(
    darkTheme: Boolean = false,
    composable: @Composable () -> Unit,
) {
    YaseyoTheme {
        val colors = YaseyoTheme.colors
        Scaffold(
            containerColor = Color(colors.backgroundBase),
        ) {
            Box(modifier = Modifier.padding(it).fillMaxSize()) {
                composable()
            }
        }
    }
}
