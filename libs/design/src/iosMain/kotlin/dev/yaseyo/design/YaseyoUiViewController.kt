package dev.yaseyo.design

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController

fun createYaseyoUiViewController(screen: @Composable () -> Unit) =
    ComposeUIViewController {
        YaseyoTheme {
            Scaffold(
                containerColor = YaseyoTheme.colors.backgroundBase,
                snackbarHost = {
                    SnackbarHost(hostState = LocalSnackBarHostState.current)
                },
            ) {
                Box(modifier = Modifier.padding(it).fillMaxSize()) {
                    screen()
                }
            }
        }
    }
