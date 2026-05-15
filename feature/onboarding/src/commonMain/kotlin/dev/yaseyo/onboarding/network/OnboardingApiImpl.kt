package dev.yaseyo.onboarding.network

import dev.yaseyo.onboarding.model.ActivityLevelOption
import dev.yaseyo.onboarding.model.GoalSuggestion
import dev.yaseyo.onboarding.model.OnboardingStep
import dev.yaseyo.onboarding.model.OnboardingSummary
import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.datetime.LocalDate

internal class OnboardingApiImpl(
    private val client: HttpClient,
) : OnboardingApi {
    override suspend fun getSteps(): List<OnboardingStep> =
        client
            .get("/onboarding/steps")
            .body<List<OnboardingStepNet>>()
            .map { OnboardingStep(key = it.key, title = it.title, subtitle = it.subtitle) }

    override suspend fun getActivityLevels(): List<ActivityLevelOption> =
        client
            .get("/user/activity-levels")
            .body<List<ActivityLevelNet>>()
            .mapNotNull { net ->
                val level = when (net.level) {
                    "SEDENTARY" -> ActivityLevel.Sedentary
                    "LIGHTLY_ACTIVE" -> ActivityLevel.LightlyActive
                    else -> return@mapNotNull null
                }
                ActivityLevelOption(level = level, label = net.label, description = net.description)
            }

    override suspend fun getGoalSuggestion(
        sex: Sex,
        heightCm: Int,
        dateOfBirth: LocalDate,
        currentWeightKg: Int,
    ): GoalSuggestion {
        val net = client
            .post("/onboarding/goal-suggestion") {
                contentType(ContentType.Application.Json)
                setBody(
                    GoalSuggestionRequestNet(
                        sex = sex.toApiValue(),
                        height = heightCm.toDouble(),
                        dateOfBirth = dateOfBirth.toString(),
                        currentWeightKg = currentWeightKg.toDouble(),
                    ),
                )
            }.body<GoalSuggestionNet>()
        return GoalSuggestion(
            currentBmi = net.current.bmi,
            currentBodyFatPct = net.current.bodyFat?.estimatedPct,
            currentBodyFatCategory = net.current.bodyFat?.category,
            suggestedTargetKg = net.suggestedTargetKg.toInt(),
        )
    }

    override suspend fun getSummary(
        sex: Sex,
        heightCm: Int,
        dateOfBirth: LocalDate,
        currentWeightKg: Int,
        activityLevel: ActivityLevel,
        dailyDeficitKcal: Int,
    ): OnboardingSummary {
        val net = client
            .post("/onboarding/summary") {
                contentType(ContentType.Application.Json)
                setBody(
                    OnboardingSummaryRequestNet(
                        sex = sex.toApiValue(),
                        height = heightCm.toDouble(),
                        dateOfBirth = dateOfBirth.toString(),
                        currentWeightKg = currentWeightKg.toDouble(),
                        activityLevel = activityLevel.toApiValue(),
                        dailyDeficitKcal = dailyDeficitKcal,
                    ),
                )
            }.body<OnboardingSummaryNet>()
        return OnboardingSummary(
            tdee = net.tdee.toInt(),
            dailyCalorieTarget = net.dailyCalorieTarget.toInt(),
            weeklyLossKg = net.weeklyLossKg,
            proteinG = net.suggestedMacros.protein.toInt(),
            carbsG = net.suggestedMacros.carbs.toInt(),
            fatG = net.suggestedMacros.fat.toInt(),
        )
    }

    private fun Sex.toApiValue(): String =
        when (this) {
            Sex.Male -> "MALE"
            Sex.Female -> "FEMALE"
        }

    private fun ActivityLevel.toApiValue(): String =
        when (this) {
            ActivityLevel.Sedentary -> "SEDENTARY"
            ActivityLevel.LightlyActive -> "LIGHTLY_ACTIVE"
        }
}
