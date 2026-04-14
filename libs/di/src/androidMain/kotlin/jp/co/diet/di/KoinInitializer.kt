package jp.co.diet.di

import android.content.Context
import androidx.startup.Initializer
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication {
        return startKoin {
            androidContext(context)
            modules(appModules) // Add modules here
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
