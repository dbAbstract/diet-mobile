package dev.yaseyo

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Property
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework

abstract class KmpLibraryExtension {
    abstract val iosFrameworkName: Property<String>
    abstract val enableSkie: Property<Boolean>
}

class KmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.kotlin.multiplatform.library")
                apply("com.android.lint")
            }
        }

        val extension = target
            .extensions
            .create("kmpLibrary", KmpLibraryExtension::class.java)

        extension.enableSkie.convention(false)

        val kotlin = target.extensions.getByType(KotlinMultiplatformExtension::class.java)

        val iosTargets = with(kotlin) {
            listOf(
                iosArm64(),
                iosSimulatorArm64()
            ).also { targetList ->
                targetList.forEach { iosTarget ->
                    iosTarget.binaries.framework {
                        isStatic = true
                    }
                }
            }
        }

        target.afterEvaluate {
            val frameworkName = extension.iosFrameworkName.orNull

            if (frameworkName != null) {
                iosTargets.forEach { iosTarget ->
                    iosTarget.binaries.withType(Framework::class.java).configureEach {
                        baseName = frameworkName
                    }
                }
            }

            val skieEnabled = extension.enableSkie.get()
            if (skieEnabled) {
                target.plugins.apply("co.touchlab.skie")
            }
        }

        val libs = target
            .extensions
            .getByType(VersionCatalogsExtension::class.java)
            .named("libs")

        val androidLibrary = kotlin.extensions.getByName("androidLibrary")
                as KotlinMultiplatformAndroidLibraryTarget
        androidLibrary.apply {
            minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
            compileSdk = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()
        }
    }
}