package dev.yaseyo

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency

private val INTERNAL_MODULE_SUFFIXES = setOf(":impl", ":android")
private val INTERNAL_MODULE_CONSUMER_ALLOWLIST = setOf(":libs:di")

fun Project.enforceInternalModuleBoundaries() {
    afterEvaluate {
        if (path in INTERNAL_MODULE_CONSUMER_ALLOWLIST) return@afterEvaluate

        configurations.forEach { configuration ->
            configuration.dependencies
                .withType(ProjectDependency::class.java)
                .forEach { dep ->
                    val depPath = dep.dependencyProject.path
                    if (depPath == path) return@forEach
                    val suffix = INTERNAL_MODULE_SUFFIXES.firstOrNull { depPath.endsWith(it) } ?: return@forEach
                    val expectedParent = depPath.removeSuffix(suffix)
                    val isSameTree = path == expectedParent || path.startsWith("$expectedParent:")
                    if (!isSameTree) {
                        throw GradleException(
                            buildString {
                                appendLine()
                                appendLine("DESIGN BREACH ───────────────────────────────────────────────────")
                                appendLine("  Module : $path")
                                appendLine("  Depends on : $depPath  ← $suffix is internal")
                                appendLine()
                                appendLine("  $suffix modules are internal to their parent. No module outside")
                                appendLine("  the parent ('$expectedParent') is allowed to depend on them.")
                                appendLine("  The parent re-exports the public API and aggregates Koin modules.")
                                appendLine()
                                appendLine("  In $path/build.gradle.kts, replace:")
                                appendLine("    implementation(projects${depPath.replace(":", ".")})")
                                appendLine("  with:")
                                appendLine("    implementation(projects${expectedParent.replace(":", ".")})")
                                appendLine("─────────────────────────────────────────────────────────────────")
                            },
                        )
                    }
                }
        }
    }
}
