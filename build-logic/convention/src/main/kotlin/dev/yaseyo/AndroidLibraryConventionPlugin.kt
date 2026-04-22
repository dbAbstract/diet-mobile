package dev.yaseyo

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
        }

        target.enforceInternalModuleBoundaries()

        val libs =
            target
                .extensions
                .getByType(VersionCatalogsExtension::class.java)
                .named("libs")

        val android = target.extensions.getByType(LibraryExtension::class.java)
        android.compileSdk =
            libs
                .findVersion("android-compileSdk")
                .get()
                .requiredVersion
                .toInt()
        android.defaultConfig.minSdk =
            libs
                .findVersion("android-minSdk")
                .get()
                .requiredVersion
                .toInt()
        android.compileOptions.sourceCompatibility = JavaVersion.VERSION_11
        android.compileOptions.targetCompatibility = JavaVersion.VERSION_11

        target.tasks.withType(KotlinCompile::class.java).configureEach {
            compilerOptions.jvmTarget.set(JvmTarget.JVM_11)
        }
    }
}
