plugins {
    id("yaseyo.kmp.library")
    alias(libs.plugins.composeCompiler)
}

kotlin {
    android {
        namespace = "dev.yaseyo.navigation"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.koin.core)
            }
        }

        androidMain {
            dependencies {
                api(libs.androidx.navigation3.runtime)
                implementation(libs.androidx.navigation3.ui)
                implementation(libs.koin.core)
                implementation(libs.koin.android)
                implementation(libs.compose.material3)
            }
        }
    }
}
