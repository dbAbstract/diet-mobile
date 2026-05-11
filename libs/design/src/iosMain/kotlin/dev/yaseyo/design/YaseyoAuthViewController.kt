package dev.yaseyo.design

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController

fun createYaseyoUiViewController(screen: @Composable () -> Unit) =
    ComposeUIViewController {
        YaseyoTheme {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = LocalSnackBarHostState.current)
                },
            ) {
                screen()
            }
        }
    }
