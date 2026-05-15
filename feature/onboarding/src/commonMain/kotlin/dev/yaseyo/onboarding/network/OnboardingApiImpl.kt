package dev.yaseyo.onboarding.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class OnboardingApiImpl(
    private val client: HttpClient,
) : OnboardingApi {
    override suspend fun getSteps(): List<OnboardingStepNet> = client.get("/onboarding/steps").body()

    override suspend fun getActivityLevels(): List<ActivityLevelNet> = client.get("/user/activity-levels").body()
}
