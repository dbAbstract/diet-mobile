plugins {
    id("yaseyo.kmp.library")
}

kmpLibrary {
    iosFrameworkName = "Coroutines"
}

kotlin {
    androidLibrary {
        namespace = "dev.yaseyo.coroutines"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                api(libs.kotlinx.coroutines.core)
                implementation(libs.koin.core)
            }
        }
    }
}
