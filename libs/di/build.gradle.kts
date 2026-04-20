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
            export(projects.libs.presentation)
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
                api(projects.libs.presentation)

                // Features
                implementation(projects.feature.onboarding.data)
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
