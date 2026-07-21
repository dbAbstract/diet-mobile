package dev.yaseyo.di

import dev.yaseyo.network.NetworkConfig

// 10.0.2.2 is the Android emulator's alias for localhost
internal actual val networkConfig = NetworkConfig(baseUrl = "http://10.0.2.2:3000")
