package dev.yaseyo.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.mp.KoinPlatform

actual fun createDataStore(): DataStore<Preferences> =
    createDataStore {
        val context = KoinPlatform.getKoin().get<Context>()
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }
