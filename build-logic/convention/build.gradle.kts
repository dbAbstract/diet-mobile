plugins {
    `kotlin-dsl`
}

group = "jp.co.diet.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.gradlePlugin.android)
    compileOnly(libs.gradlePlugin.kotlin)
}

gradlePlugin {
    plugins {
        register("kmpLibrary") {
            id = "diet.kmp.library"
            implementationClass = "KmpLibraryConventionPlugin"
        }
    }
}
