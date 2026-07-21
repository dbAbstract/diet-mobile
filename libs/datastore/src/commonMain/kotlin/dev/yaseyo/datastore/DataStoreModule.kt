package dev.yaseyo.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.dsl.module

val dataStoreModule = module {
    single<DataStore<Preferences>> { createDataStore() }
}
