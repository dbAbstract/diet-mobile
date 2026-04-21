package dev.yaseyo.di

import android.content.Context
import androidx.startup.Initializer
import dev.yaseyo.navigation.navigationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication =
        startKoin {
            androidContext(context)
            modules(appModules + navigationModule)
        }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
