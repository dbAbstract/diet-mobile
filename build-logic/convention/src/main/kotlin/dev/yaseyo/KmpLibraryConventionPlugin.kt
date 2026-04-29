package dev.yaseyo

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Property
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

abstract class KmpLibraryExtension {
    abstract val iosFrameworkName: Property<String>
    abstract val enableSkie: Property<Boolean>
}

private val UMBRELLA_FRAMEWORK_PROJECTS = setOf(":libs:di")

class KmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.kotlin.multiplatform.library")
                apply("com.android.lint")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }
        }

        val extension =
            target
                .extensions
                .create("kmpLibrary", KmpLibraryExtension::class.java)

        extension.enableSkie.convention(false)

        val kotlin = target.extensions.getByType(KotlinMultiplatformExtension::class.java)

        val iosTargets =
            with(kotlin) {
                listOf(
                    iosArm64(),
                    iosSimulatorArm64(),
                )
            }

        target.enforceInternalModuleBoundaries()

        target.afterEvaluate {
            val frameworkName = extension.iosFrameworkName.orNull

            if (frameworkName != null && target.path !in UMBRELLA_FRAMEWORK_PROJECTS) {
                throw GradleException(
                    buildString {
                        appendLine(
                            "Module '${target.path}' sets kmpLibrary.iosFrameworkName = \"$frameworkName\", but iOS framework production is restricted to: $UMBRELLA_FRAMEWORK_PROJECTS",
                        )
                        appendLine(
                            "Why: iOS can load at most one Kotlin/Native framework per process — each framework ships its own K/N runtime and loading two triggers a runtime-injection crash at app launch.",
                        )
                        appendLine(
                            "Fix: remove 'iosFrameworkName' from this module. Expose types to Swift by adding api(...) + export(...) in the umbrella module (${UMBRELLA_FRAMEWORK_PROJECTS.first()}) instead.",
                        )
                    },
                )
            }

            if (frameworkName != null) {
                iosTargets.forEach { iosTarget ->
                    iosTarget.binaries.framework {
                        baseName = frameworkName
                        isStatic = true
                    }
                }
            }

            val skieEnabled = extension.enableSkie.get()
            if (skieEnabled) {
                target.plugins.apply("co.touchlab.skie")
            }
        }

        val libs =
            target
                .extensions
                .getByType(VersionCatalogsExtension::class.java)
                .named("libs")

        val androidLibrary =
            kotlin.extensions.getByName("androidLibrary")
                as KotlinMultiplatformAndroidLibraryTarget
        androidLibrary.apply {
            minSdk =
                libs
                    .findVersion("android-minSdk")
                    .get()
                    .requiredVersion
                    .toInt()
            compileSdk =
                libs
                    .findVersion("android-compileSdk")
                    .get()
                    .requiredVersion
                    .toInt()
        }
    }
}
