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
    }
}
