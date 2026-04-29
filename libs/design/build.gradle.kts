plugins {
    id("yaseyo.kmp.library")
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidLibrary {
        namespace = "dev.yaseyo.design"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.compose.ui)
                implementation(libs.compose.material3)
                implementation(libs.compose.foundation)
            }
        }
    }
}
