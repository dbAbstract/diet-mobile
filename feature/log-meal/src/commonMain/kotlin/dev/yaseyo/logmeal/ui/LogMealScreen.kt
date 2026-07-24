package dev.yaseyo.logmeal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yaseyo.design.YaseyoScaffold
import dev.yaseyo.design.YaseyoScreenPreview
import dev.yaseyo.design.YaseyoTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LogMealScreen(viewModel: LogMealViewModel = koinViewModel()) {
    LogMealScreen(onBackClick = viewModel::onBack)
}

@Composable
private fun LogMealScreen(onBackClick: () -> Unit) {
    YaseyoScaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = YaseyoTheme.colors.contentPrimary,
                    )
                }
                Text(
                    text = "Log Meal",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = YaseyoTheme.colors.contentPrimary,
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Meal logging is coming soon.",
                    color = YaseyoTheme.colors.contentSecondary,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun LogMealScreenPreview() =
    YaseyoScreenPreview {
        LogMealScreen(onBackClick = {})
    }
