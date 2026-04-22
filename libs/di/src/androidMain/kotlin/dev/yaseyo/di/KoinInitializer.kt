package dev.yaseyo.di

import android.content.Context
import androidx.startup.Initializer
import dev.yaseyo.navigation.navigationAndroidModule
import dev.yaseyo.onboarding.impl.onboardingFeatureAndroidModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication =
        startKoin {
            androidContext(context)
            modules(appModules + navigationAndroidModule + onboardingFeatureAndroidModule)
        }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
