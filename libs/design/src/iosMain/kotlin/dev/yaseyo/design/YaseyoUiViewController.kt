package dev.yaseyo.design

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController

fun createYaseyoUiViewController(screen: @Composable () -> Unit) =
    ComposeUIViewController {
        YaseyoTheme {
            YaseyoScaffold { padding ->
                Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                    screen()
                }
            }
        }
    }
