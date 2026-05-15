plugins {
    id("yaseyo.kmp.library")
}

kmpLibrary {}

kotlin {
    android {
        namespace = "dev.yaseyo.datastore"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.androidx.datastore)
                implementation(libs.androidx.datastore.preferences)
                implementation(libs.koin.core)
            }
        }
    }
}
