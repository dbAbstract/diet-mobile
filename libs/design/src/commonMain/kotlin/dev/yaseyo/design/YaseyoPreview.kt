package dev.yaseyo.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun YaseyoPreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    composable: @Composable () -> Unit,
) {
    YaseyoTheme(darkTheme = darkTheme) {
        val colors = YaseyoTheme.colors
        MaterialTheme {
            Scaffold(
                containerColor = colors.backgroundBase,
            ) {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    composable()
                }
            }
        }
    }
}
