package dev.yaseyo.onboarding.network

internal interface OnboardingApi {
    suspend fun getSteps(): List<OnboardingStepNet>

    suspend fun getActivityLevels(): List<ActivityLevelNet>

    suspend fun getGoalSuggestion(request: GoalSuggestionRequestNet): GoalSuggestionNet

    suspend fun getSummary(request: OnboardingSummaryRequestNet): OnboardingSummaryNet
}
