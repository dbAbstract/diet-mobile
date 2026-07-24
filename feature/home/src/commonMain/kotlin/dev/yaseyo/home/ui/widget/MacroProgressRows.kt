package dev.yaseyo.home.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import dev.yaseyo.design.YaseyoPreview
import dev.yaseyo.design.YaseyoTheme

internal data class MacroProgressRowData(
    val label: String,
    val progress: () -> Float,
    val currentGrams: Double,
    val targetGrams: Double,
)

@Composable
internal fun MacroProgressRows(
    rows: List<MacroProgressRowData>,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val horizontalSpacingPx = with(density) { 16.dp.roundToPx() }
    val verticalSpacingPx = with(density) { 12.dp.roundToPx() }
    val indicatorHeightPx = with(density) { 16.dp.roundToPx() }

    Layout(
        modifier = modifier.fillMaxWidth(),
        content = {
            rows.forEach { row ->
                Text(
                    text = row.label,
                    color = YaseyoTheme.colors.contentPrimary,
                    maxLines = 1,
                )
                LinearProgressIndicator(
                    progress = row.progress,
                    strokeCap = StrokeCap.Butt,
                    color = YaseyoTheme.colors.accentDefault,
                    trackColor = YaseyoTheme.colors.accentMuted,
                    gapSize = 0.dp,
                )
                Text(
                    text = "${row.currentGrams.toInt()} / ${row.targetGrams.toInt()}g",
                    color = YaseyoTheme.colors.contentPrimary,
                    maxLines = 1,
                )
            }
        },
    ) { measurables, constraints ->
        val labelMeasurables = rows.indices.map { measurables[it * 3] }
        val indicatorMeasurables = rows.indices.map { measurables[it * 3 + 1] }
        val fractionMeasurables = rows.indices.map { measurables[it * 3 + 2] }

        val labelColumnWidth = labelMeasurables.maxOf { it.maxIntrinsicWidth(Int.MAX_VALUE) }
        val fractionColumnWidth = fractionMeasurables.maxOf { it.maxIntrinsicWidth(Int.MAX_VALUE) }
        val indicatorWidth = (constraints.maxWidth - labelColumnWidth - fractionColumnWidth - horizontalSpacingPx * 2)
            .coerceAtLeast(0)

        val labelPlaceables = labelMeasurables.map { it.measure(Constraints.fixedWidth(labelColumnWidth)) }
        val indicatorPlaceables = indicatorMeasurables.map {
            it.measure(Constraints.fixed(indicatorWidth, indicatorHeightPx))
        }
        val fractionPlaceables = fractionMeasurables.map { it.measure(Constraints.fixedWidth(fractionColumnWidth)) }

        val rowHeights = rows.indices.map { i ->
            maxOf(labelPlaceables[i].height, indicatorPlaceables[i].height, fractionPlaceables[i].height)
        }
        val totalHeight = rowHeights.sum() + verticalSpacingPx * (rows.size - 1).coerceAtLeast(0)

        layout(width = constraints.maxWidth, height = totalHeight) {
            var y = 0
            rows.indices.forEach { i ->
                val rowHeight = rowHeights[i]
                val label = labelPlaceables[i]
                val indicator = indicatorPlaceables[i]
                val fraction = fractionPlaceables[i]

                label.place(x = 0, y = y + (rowHeight - label.height) / 2)
                indicator.place(
                    x = labelColumnWidth + horizontalSpacingPx,
                    y = y + (rowHeight - indicator.height) / 2,
                )
                fraction.place(
                    x = labelColumnWidth + horizontalSpacingPx + indicatorWidth + horizontalSpacingPx,
                    y = y + (rowHeight - fraction.height) / 2,
                )

                y += rowHeight + verticalSpacingPx
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun MacroProgressRowsPreview() =
    YaseyoPreview {
        MacroProgressRows(
            modifier = Modifier.padding(16.dp),
            rows = listOf(
                MacroProgressRowData(
                    label = "Protein",
                    progress = { 0.5f },
                    currentGrams = 90.0,
                    targetGrams = 180.0,
                ),
                MacroProgressRowData(
                    label = "Carbs",
                    progress = { 0.5f },
                    currentGrams = 1100.0,
                    targetGrams = 2200.0,
                ),
                MacroProgressRowData(
                    label = "Fat",
                    progress = { 0.57f },
                    currentGrams = 40.0,
                    targetGrams = 70.0,
                ),
            ),
        )
    }
