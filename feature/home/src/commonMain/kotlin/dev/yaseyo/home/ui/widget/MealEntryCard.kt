package dev.yaseyo.home.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yaseyo.dailylog.api.MealEntry
import dev.yaseyo.dailylog.api.MealType
import dev.yaseyo.design.YaseyoPreview
import dev.yaseyo.design.YaseyoTheme
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

@Composable
internal fun MealEntryCard(
    mealEntry: MealEntry,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.backgroundSubtle)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.accentSubtle)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    text = mealEntry.mealType.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.contentPrimary,
                )
                Text(
                    text = mealEntry.loggedAt.formatTime(),
                    fontSize = 12.sp,
                    color = colors.contentTertiary,
                )
            }
            Text(
                text = "${mealEntry.kcal.toInt()} kcal",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.contentPrimary,
            )
        }

        val notes = mealEntry.notes
        if (notes != null) {
            Text(
                text = notes,
                fontSize = 14.sp,
                color = colors.contentSecondary,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MacroStat(label = "Protein", grams = mealEntry.protein)
            MacroStat(label = "Carbs", grams = mealEntry.carbs)
            MacroStat(label = "Fat", grams = mealEntry.fat)
        }
    }
}

@Composable
private fun MacroStat(
    label: String,
    grams: Double,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(text = label, fontSize = 13.sp, color = colors.contentTertiary)
        Text(
            text = "${grams.toInt()}g",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = colors.contentSecondary,
        )
    }
}

private fun Instant.formatTime(): String {
    val time = toLocalDateTime(TimeZone.currentSystemDefault()).time
    val hour = time.hour.toString().padStart(2, '0')
    val minute = time.minute.toString().padStart(2, '0')
    return "$hour:$minute"
}

private val previewMealEntry = MealEntry(
    id = "meal_1",
    dailyLogId = "log_1",
    mealType = MealType.Breakfast,
    quantity = 1.0,
    notes = "Oatmeal with banana and honey",
    foodItemId = "food_1",
    recipeId = null,
    kcal = 420.0,
    protein = 18.0,
    carbs = 65.0,
    fat = 9.0,
    fiber = 6.0,
    loggedAt = Instant.parse("2026-07-24T08:30:00Z"),
    createdAt = Instant.parse("2026-07-24T08:30:00Z"),
    updatedAt = Instant.parse("2026-07-24T08:30:00Z"),
)

@PreviewLightDark
@Composable
private fun MealEntryCardPreview() =
    YaseyoPreview {
        MealEntryCard(
            modifier = Modifier.padding(16.dp),
            mealEntry = previewMealEntry,
        )
    }

@PreviewLightDark
@Composable
private fun MealEntryCardNoNotesPreview() =
    YaseyoPreview {
        MealEntryCard(
            modifier = Modifier.padding(16.dp),
            mealEntry = previewMealEntry.copy(
                mealType = MealType.Lunch,
                notes = null,
                kcal = 680.0,
                protein = 42.0,
                carbs = 70.0,
                fat = 22.0,
            ),
        )
    }

@PreviewLightDark
@Composable
private fun MealEntryCardListPreview() =
    YaseyoPreview {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            MealEntryCard(mealEntry = previewMealEntry)
            Spacer(modifier = Modifier.height(0.dp))
            MealEntryCard(
                mealEntry = previewMealEntry.copy(
                    mealType = MealType.Snack,
                    notes = null,
                    kcal = 180.0,
                    protein = 4.0,
                    carbs = 22.0,
                    fat = 8.0,
                    loggedAt = Instant.parse("2026-07-24T15:45:00Z"),
                ),
            )
        }
    }
