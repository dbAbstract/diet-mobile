plugins {
    id("yaseyo.kmp.library")
}

kotlin {
    androidLibrary {
        namespace = "dev.yaseyo.onboarding"
    }

    sourceSets {
        androidMain.dependencies {
            api(projects.feature.onboarding.api)
            implementation(projects.feature.onboarding.impl)
            implementation(libs.koin.core)
        }
    }
}
