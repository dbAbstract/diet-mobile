package dev.yaseyo.di

import dev.yaseyo.network.NetworkConfig

internal actual val networkConfig = NetworkConfig(baseUrl = "http://localhost:8080")
