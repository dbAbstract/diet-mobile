package dev.yaseyo.network

import dev.yaseyo.auth.api.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule =
    module {
        single<HttpClient> {
            val config = get<NetworkConfig>()
            val authRepository = get<AuthRepository>()

            HttpClient(createHttpClientEngine()) {
                install(Auth) {
                    bearer {
                        loadTokens {
                            val token = authRepository.getIdToken()
                            token?.let { BearerTokens(accessToken = it, refreshToken = "") }
                        }
                    }
                }
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            isLenient = true
                        },
                    )
                }
                HttpResponseValidator {
                    validateResponse { response ->
                        if (!response.status.isSuccess()) {
                            throw ResponseException(response, "HTTP ${response.status.value}")
                        }
                    }
                }
                install(Logging) {
                    level = LogLevel.ALL
                }
                defaultRequest {
                    url(config.baseUrl)
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }
