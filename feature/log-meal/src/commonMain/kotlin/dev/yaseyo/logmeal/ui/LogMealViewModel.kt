package dev.yaseyo.logmeal.ui

import androidx.lifecycle.ViewModel
import dev.yaseyo.navigation.AppRouter

internal class LogMealViewModel(
    private val appRouter: AppRouter,
) : ViewModel() {
    fun onBack() {
        appRouter.goBack()
    }
}
