plugins {
    id("yaseyo.kmp.library")
}

kotlin {
    androidLibrary {
        namespace = "dev.yaseyo.coroutines.testing"
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.libs.coroutines)
                api(libs.kotlinx.coroutines.test)
            }
        }
    }
}
