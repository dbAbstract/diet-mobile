package dev.yaseyo.onboarding.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class OnboardingApiImpl(
    private val client: HttpClient,
) : OnboardingApi {
    override suspend fun getSteps(): List<OnboardingStepNet> = client.get("/onboarding/steps").body()

    override suspend fun getActivityLevels(): List<ActivityLevelNet> = client.get("/user/activity-levels").body()

    override suspend fun getGoalSuggestion(request: GoalSuggestionRequestNet): GoalSuggestionNet =
        client
            .post("/onboarding/goal-suggestion") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()

    override suspend fun getSummary(request: OnboardingSummaryRequestNet): OnboardingSummaryNet =
        client
            .post("/onboarding/summary") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
}
