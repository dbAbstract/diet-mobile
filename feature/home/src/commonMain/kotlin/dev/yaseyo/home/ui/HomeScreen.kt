package dev.yaseyo.home.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.yaseyo.design.YaseyoPreview
import dev.yaseyo.design.YaseyoTheme
import dev.yaseyo.home.ui.widget.KcalProgressHero
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock

@Composable
internal fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(state = state)
}

@Composable
private fun HomeScreen(state: HomeUiState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        stickyHeader {
            Text(
                modifier = Modifier.padding(16.dp),
                text = Clock
                    .System
                    .now()
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date
                    .toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = YaseyoTheme.colors.contentPrimary,
            )

            HorizontalDivider()
        }

        item {
            AnimatedContent(
                modifier = Modifier.fillMaxSize(),
                targetState = state,
                contentKey = {
                    it::class.simpleName
                },
            ) { state ->
                when (state) {
                    is HomeUiState.Content -> {
                        KcalProgressHero(
                            modifier = Modifier.padding(16.dp),
                            progress = {
                                state.progress
                            },
                            currentKcal = state.currentKcal,
                            baseTargetKcal = state.baseTargetKcal,
                            activityKcal = state.activityKcal,
                        )
                    }

                    HomeUiState.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Something went wrong. Please try again.",
                                color = YaseyoTheme.colors.contentSecondary,
                                fontSize = 16.sp,
                            )
                        }
                    }

                    HomeUiState.Initializing -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Loading...",
                                color = YaseyoTheme.colors.contentSecondary,
                                fontSize = 16.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenContentPreview() =
    YaseyoPreview {
        HomeScreen(
            state = HomeUiState.Content(
                currentKcal = 1200,
                activityKcal = 300,
                baseTargetKcal = 2200,
                progress = (1200.0 / 2500.0).toFloat(),
            ),
        )
    }

@PreviewLightDark
@Composable
private fun HomeScreenErrorPreview() =
    YaseyoPreview {
        HomeScreen(state = HomeUiState.Error)
    }

@PreviewLightDark
@Composable
private fun HomeScreenLoadingPreview() =
    YaseyoPreview {
        HomeScreen(state = HomeUiState.Initializing)
    }
