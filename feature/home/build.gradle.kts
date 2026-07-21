plugins {
    id("yaseyo.kmp.library")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    android {
        namespace = "dev.yaseyo.home"

        withHostTestBuilder {
            sourceSetTreeName = "test"
        }

        withDeviceTestBuilder {
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.koin.core)
                implementation(libs.koin.core.viewmodel)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                api(libs.kotlinx.coroutines.core)

                implementation(projects.libs.coroutines)
                api(projects.libs.navigation)
                implementation(projects.libs.design)

                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.uiToolingPreview)
                implementation(libs.compose.material.icons.extended)
                implementation(libs.compose.navigationevent)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.turbine)
                implementation(projects.libs.coroutines.testing)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.koin.android)
                implementation(libs.koin.compose.nav3)
                implementation(libs.androidx.ui.tooling)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.testExt.junit)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}
