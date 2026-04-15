package dev.yaseyo.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

internal actual fun createHttpClientEngine(): HttpClientEngine = OkHttp.create()
