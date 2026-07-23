package dev.yaseyo.dailylog.impl.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.datetime.LocalDate

internal class DailyLogApiImpl(
    private val client: HttpClient,
) : DailyLogApi {
    companion object {
        private const val LOG_PATH = "/logs/"
    }

    override suspend fun getLog(date: LocalDate): DailyLogNet =
        client
            .get("$LOG_PATH$date/summary")
            .body()

    override suspend fun logEntry(
        date: LocalDate,
        request: LogEntryRequestNet,
    ) {
        client.post("$LOG_PATH$date/entries") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
}
