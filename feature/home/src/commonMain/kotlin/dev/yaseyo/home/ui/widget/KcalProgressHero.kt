package dev.yaseyo.home.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yaseyo.design.YaseyoPreview
import dev.yaseyo.design.YaseyoTheme

@Composable
internal fun KcalProgressHero(
    progress: () -> Float,
    currentKcal: Long,
    activityKcal: Long,
    baseTargetKcal: Long,
    modifier: Modifier = Modifier,
) {
    val effectiveKcal = baseTargetKcal + activityKcal

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f),
            progress = progress,
            color = YaseyoTheme.colors.accentDefault,
            strokeWidth = 32.dp,
            trackColor = YaseyoTheme.colors.accentMuted,
            strokeCap = StrokeCap.Butt,
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$currentKcal/$effectiveKcal",
                color = YaseyoTheme.colors.contentPrimary,
                fontSize = 32.sp,
            )
            if (activityKcal != 0L) {
                Text(
                    text = "$baseTargetKcal base + $activityKcal from activity",
                    color = YaseyoTheme.colors.contentSecondary,
                    fontSize = 13.sp,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun KcalProgressHeroEmptyPreview() =
    YaseyoPreview {
        KcalProgressHero(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            progress = { 0f },
            currentKcal = 0,
            activityKcal = 0,
            baseTargetKcal = 2000,
        )
    }

@PreviewLightDark
@Composable
private fun KcalProgressHeroPartialPreview() =
    YaseyoPreview {
        KcalProgressHero(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            progress = { 0.45f },
            currentKcal = 900,
            activityKcal = 200,
            baseTargetKcal = 2000,
        )
    }

@PreviewLightDark
@Composable
private fun KcalProgressHeroCompletePreview() =
    YaseyoPreview {
        KcalProgressHero(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            progress = { 1f },
            currentKcal = 2000,
            activityKcal = 0,
            baseTargetKcal = 2000,
        )
    }

@PreviewLightDark
@Composable
private fun KcalProgressHeroOverPreview() =
    YaseyoPreview {
        KcalProgressHero(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            progress = { 1.2f },
            currentKcal = 2200,
            activityKcal = 0,
            baseTargetKcal = 2000,
        )
    }
