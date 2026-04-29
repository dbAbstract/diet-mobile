package dev.yaseyo.di

import android.content.Context
import androidx.startup.Initializer
import org.koin.core.context.loadKoinModules

class MainModuleInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        loadKoinModules(mainModule)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(KoinInitializer::class.java)
}
