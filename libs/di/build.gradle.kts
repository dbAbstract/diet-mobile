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

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.koin.core)
                implementation(projects.libs.coroutines)
                implementation(projects.libs.auth)
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
