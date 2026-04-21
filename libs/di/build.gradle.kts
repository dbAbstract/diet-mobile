import org.jetbrains.kotlin.gradle.plugin.mpp.Framework

plugins {
    id("yaseyo.kmp.library")
}

kmpLibrary {
    iosFrameworkName = "Di"
    enableSkie = true
}

kotlin {
    androidLibrary {
        namespace = "dev.yaseyo.di"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { target ->
        target.binaries.withType<Framework>().configureEach {
            export(projects.libs.auth.api)
            export(projects.libs.user)
            export(projects.feature.onboarding.presentation)
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.koin.core)

                // Libs
                implementation(projects.libs.coroutines)
                api(projects.libs.auth.api)
                implementation(projects.libs.auth)
                implementation(projects.libs.network)
                api(projects.libs.user)

                // Features
                implementation(projects.feature.onboarding.data)
                api(projects.feature.onboarding.presentation)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.koin.android)
                implementation(libs.androidx.startup.runtime)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}
