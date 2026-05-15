rootProject.name = "Yaseyo"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":libs:domain-models")
include(":libs:user")
include(":libs:user:api")
include(":libs:user:impl")
include(":libs:auth:api")
include(":libs:auth:impl")
include(":libs:coroutines")
include(":libs:navigation")
include(":libs:design")
include(":libs:network")
include(":libs:di")
include(":libs:datastore")
include(":feature:onboarding")
