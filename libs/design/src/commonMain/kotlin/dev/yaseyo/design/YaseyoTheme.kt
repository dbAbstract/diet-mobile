package dev.yaseyo.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

private val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No AppColors provided — wrap your UI in YaseyoTheme { }")
}

val LocalSnackBarHostState = staticCompositionLocalOf<SnackbarHostState> {
    error("No SnackBarHostState provided")
}

@Composable
fun YaseyoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    CompositionLocalProvider(
        LocalAppColors provides if (darkTheme) DarkAppColors else LightAppColors,
        LocalSnackBarHostState provides snackBarHostState,
        content = content,
    )
}

object YaseyoTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current
}

@Composable
fun YaseyoScaffold(
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        containerColor = YaseyoTheme.colors.backgroundBase,
        floatingActionButton = floatingActionButton,
        snackbarHost = { SnackbarHost(hostState = LocalSnackBarHostState.current) },
        content = content,
    )
}
