package dev.yaseyo.home.ui

import androidx.lifecycle.ViewModel
import dev.yaseyo.dailylog.api.DailyLogRepository

internal class HomeViewModel(
    private val dailyLogRepository: DailyLogRepository,
) : ViewModel(),
    HomeEventHandler
