package dev.yaseyo.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * For previewing widgets/components that don't provide their own [YaseyoScaffold] —
 * wraps [composable] in a scaffold so it has a themed background/container to sit in.
 */
@Composable
fun YaseyoPreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    composable: @Composable () -> Unit,
) {
    YaseyoTheme(darkTheme = darkTheme) {
        MaterialTheme {
            YaseyoScaffold { padding ->
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    composable()
                }
            }
        }
    }
}

/**
 * For previewing full screens that already wrap themselves in [YaseyoScaffold] —
 * only provides the theme, so nesting a second scaffold doesn't hide things like the
 * screen's own floating action button behind [YaseyoPreview]'s outer scaffold.
 */
@Composable
fun YaseyoScreenPreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    composable: @Composable () -> Unit,
) {
    YaseyoTheme(darkTheme = darkTheme) {
        MaterialTheme {
            composable()
        }
    }
}
