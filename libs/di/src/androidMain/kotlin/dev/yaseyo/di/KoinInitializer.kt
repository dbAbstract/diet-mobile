package dev.yaseyo.di

import android.content.Context
import androidx.startup.Initializer
import dev.yaseyo.home.homeFeatureAndroidModule
import dev.yaseyo.navigation.navigationAndroidModule
import dev.yaseyo.onboarding.onboardingFeatureAndroidModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication =
        startKoin {
            androidContext(context)

            val modules = appModules + listOf(
                navigationAndroidModule,
                onboardingFeatureAndroidModule,
                homeFeatureAndroidModule,
            )
            modules(modules)
        }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
