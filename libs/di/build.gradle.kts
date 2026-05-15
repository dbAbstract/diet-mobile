import org.jetbrains.kotlin.gradle.plugin.mpp.Framework

plugins {
    id("yaseyo.kmp.library")
}

kmpLibrary {
    iosFrameworkName = "Di"
    enableSkie = true
}

kotlin {
    android {
        namespace = "dev.yaseyo.di"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { target ->
        target.binaries.withType<Framework>().configureEach {
            export(projects.libs.auth.api)
            export(projects.libs.user)
            export(projects.libs.navigation)
            export(projects.feature.onboarding)
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.koin.core)

                // Libs
                implementation(projects.libs.coroutines)
                implementation(projects.libs.auth)
                implementation(projects.libs.network)
                implementation(projects.libs.datastore)

                // Exported Libs
                api(projects.libs.auth.api)
                api(projects.libs.user)
                api(projects.libs.navigation)

                // Features
                api(projects.feature.onboarding)
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
