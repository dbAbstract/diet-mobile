package dev.yaseyo.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

internal fun createDataStore(storePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            storePath.invoke().toPath()
        },
    )

expect fun createDataStore(): DataStore<Preferences>

internal const val DATA_STORE_FILE_NAME = "yaseyo.preferences_pb"
