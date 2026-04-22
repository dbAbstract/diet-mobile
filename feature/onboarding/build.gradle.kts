plugins {
    id("yaseyo.kmp.library")
}

kotlin {
    androidLibrary {
        namespace = "dev.yaseyo.onboarding"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.feature.onboarding.api)
        }
    }
}
