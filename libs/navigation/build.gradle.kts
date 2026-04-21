plugins {
    id("yaseyo.kmp.library")
}

kotlin {
    androidLibrary {
        namespace = "dev.yaseyo.navigation"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
            }
        }

        androidMain {
            dependencies {
                api(libs.androidx.navigation3.runtime)
                implementation(libs.koin.core)
                implementation(libs.koin.android)
            }
        }
    }
}
