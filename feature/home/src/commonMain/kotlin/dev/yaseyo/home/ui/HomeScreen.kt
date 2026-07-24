package dev.yaseyo.home.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import dev.yaseyo.dailylog.api.MealEntry
import dev.yaseyo.dailylog.api.MealType
import dev.yaseyo.design.YaseyoPreview
import dev.yaseyo.design.YaseyoScaffold
import dev.yaseyo.design.YaseyoTheme
import dev.yaseyo.home.ui.widget.KcalProgressHero
import dev.yaseyo.home.ui.widget.MacroProgressRowData
import dev.yaseyo.home.ui.widget.MacroProgressRows
import dev.yaseyo.home.ui.widget.MealEntryCard
import dev.yaseyo.home.util.currentDayOfWeek
import dev.yaseyo.home.util.now
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Instant

@Composable
internal fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    YaseyoScaffold { padding ->
        HomeScreen(state = state, modifier = Modifier.padding(padding))
    }
}

@Composable
private fun HomeScreen(
    state: HomeUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "${currentDayOfWeek()} – ${now().date}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = YaseyoTheme.colors.contentPrimary,
        )

        HorizontalDivider()

        AnimatedContent(
            modifier = Modifier.weight(1f),
            targetState = state,
            contentKey = {
                it::class.simpleName
            },
        ) { state ->
            when (state) {
                is HomeUiState.Content -> {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        item {
                            Text(
                                text = "Today's Progress",
                                color = YaseyoTheme.colors.contentPrimary,
                                fontSize = 24.sp,
                            )
                        }
                        item {
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
                        item {
                            Text(
                                text = "Macro Progress",
                                color = YaseyoTheme.colors.contentPrimary,
                                fontSize = 24.sp,
                            )
                        }
                        item {
                            MacroProgressRows(
                                rows = listOf(
                                    MacroProgressRowData(
                                        label = "Protein",
                                        progress = { state.macroStatus.proteinProgress },
                                        currentGrams = state.macroStatus.currentProtein,
                                        targetGrams = state.macroStatus.targetProtein,
                                    ),
                                    MacroProgressRowData(
                                        label = "Carbs",
                                        progress = { state.macroStatus.carbsProgress },
                                        currentGrams = state.macroStatus.currentCarbs,
                                        targetGrams = state.macroStatus.targetCarbs,
                                    ),
                                    MacroProgressRowData(
                                        label = "Fat",
                                        progress = { state.macroStatus.fatProgress },
                                        currentGrams = state.macroStatus.currentFat,
                                        targetGrams = state.macroStatus.targetFat,
                                    ),
                                ),
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        item {
                            Text(
                                text = "Meal Entries",
                                color = YaseyoTheme.colors.contentPrimary,
                                fontSize = 24.sp,
                            )
                        }

                        items(
                            items = state.mealEntries,
                            key = { it.id },
                        ) { mealEntry ->
                            MealEntryCard(mealEntry = mealEntry)
                        }
                    }
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
                macroStatus = HomeUiState.Content.MacroStatus(
                    currentProtein = 90.0,
                    targetProtein = 180.0,
                    proteinProgress = (90.0 / 180.0).toFloat(),
                    currentCarbs = 110.0,
                    targetCarbs = 220.0,
                    carbsProgress = (110.0 / 220.0).toFloat(),
                    currentFat = 40.0,
                    targetFat = 70.0,
                    fatProgress = (40.0 / 70.0).toFloat(),
                ),
                mealEntries = listOf(
                    previewMealEntry(
                        id = "meal_1",
                        mealType = MealType.Breakfast,
                        notes = "Oatmeal with banana and honey",
                        kcal = 420.0,
                        protein = 18.0,
                        carbs = 65.0,
                        fat = 9.0,
                        loggedAt = "2026-07-24T08:30:00Z",
                    ),
                    previewMealEntry(
                        id = "meal_2",
                        mealType = MealType.Lunch,
                        notes = null,
                        kcal = 680.0,
                        protein = 42.0,
                        carbs = 70.0,
                        fat = 22.0,
                        loggedAt = "2026-07-24T12:15:00Z",
                    ),
                    previewMealEntry(
                        id = "meal_3",
                        mealType = MealType.Snack,
                        notes = null,
                        kcal = 180.0,
                        protein = 4.0,
                        carbs = 22.0,
                        fat = 8.0,
                        loggedAt = "2026-07-24T15:45:00Z",
                    ),
                ),
            ),
        )
    }

private fun previewMealEntry(
    id: String,
    mealType: MealType,
    notes: String?,
    kcal: Double,
    protein: Double,
    carbs: Double,
    fat: Double,
    loggedAt: String,
) = MealEntry(
    id = id,
    dailyLogId = "log_1",
    mealType = mealType,
    quantity = 1.0,
    notes = notes,
    foodItemId = "food_1",
    recipeId = null,
    kcal = kcal,
    protein = protein,
    carbs = carbs,
    fat = fat,
    fiber = 4.0,
    loggedAt = Instant.parse(loggedAt),
    createdAt = Instant.parse(loggedAt),
    updatedAt = Instant.parse(loggedAt),
)

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
