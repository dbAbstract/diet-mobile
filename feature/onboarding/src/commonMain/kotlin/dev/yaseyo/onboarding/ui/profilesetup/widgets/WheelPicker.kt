package dev.yaseyo.onboarding.ui.profilesetup.widgets

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yaseyo.design.YaseyoTheme
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter

internal val ItemHeight: Dp = 44.dp

/**
 * Drum-style scroll picker that snaps to items.
 *
 * One empty sentinel is added at each end so the first and last real items can
 * be centered. The centered item index (0-based) is reported via [onIndexChanged]
 * after the user lifts their finger.
 */
@Composable
internal fun WheelPicker(
    items: List<String>,
    selectedIndex: Int,
    onIndexChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = YaseyoTheme.colors
    val paddedItems = remember(items) { listOf("") + items + listOf("") }

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = selectedIndex,
    )
    val snapBehavior = rememberSnapFlingBehavior(listState)

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .drop(1) // skip the initial false value before any scroll
            .filter { !it }
            .collect {
                val centeredIndex = listState.firstVisibleItemIndex
                if (centeredIndex in items.indices) {
                    onIndexChanged(centeredIndex)
                }
            }
    }

    Box(modifier = modifier.height(ItemHeight * 3)) {
        LazyColumn(
            state = listState,
            flingBehavior = snapBehavior,
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(paddedItems) { _, item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ItemHeight),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = item,
                        fontSize = 16.sp,
                        color = colors.contentPrimary,
                    )
                }
            }
        }
    }
}
