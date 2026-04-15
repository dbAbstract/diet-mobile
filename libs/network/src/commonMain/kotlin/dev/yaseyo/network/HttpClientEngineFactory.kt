package dev.yaseyo.network

import io.ktor.client.engine.HttpClientEngine

internal expect fun createHttpClientEngine(): HttpClientEngine
