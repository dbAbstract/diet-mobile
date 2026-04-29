plugins {
    id("yaseyo.android.library")
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "dev.yaseyo.onboarding.android"
}

dependencies {
    implementation(projects.feature.onboarding.api)

    implementation(libs.kotlin.stdlib)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose.nav3)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
}
