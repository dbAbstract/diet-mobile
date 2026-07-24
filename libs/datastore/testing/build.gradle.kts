plugins {
    id("yaseyo.kmp.library")
}

kotlin {
    androidLibrary {
        namespace = "dev.yaseyo.datastore.testing"
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.androidx.datastore)
                api(libs.androidx.datastore.preferences)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}
