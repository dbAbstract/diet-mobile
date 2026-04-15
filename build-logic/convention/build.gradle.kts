plugins {
    `kotlin-dsl`
}

group = "dev.yaseyo.buildlogic"

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
            id = "yaseyo.kmp.library"
            implementationClass = "dev.yaseyo.KmpLibraryConventionPlugin"
        }
    }
}
