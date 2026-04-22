---
name: working-with-gradle
description: This skill is to instruct you how to go about configuring a new gradle module or altering the gradle configuration of this project by adding/removing/updating dependencies.
---

# Working with Gradle

## General rules

- When adding a new module, pick a convention plugin from `build-logic` — do not hand-roll `android { }` / `kotlin { }` config.
- When adding a new plugin to the catalog, also add it to the project-level `build.gradle.kts` if it needs to be on the classpath.
- When you find yourself repeating config across modules, promote it into an existing convention plugin (or suggest creating a new one) rather than duplicating.

## Convention plugins

Two convention plugins cover all library modules:

### `yaseyo.kmp.library`
KMP modules. Applies:
- `org.jetbrains.kotlin.multiplatform`
- `com.android.kotlin.multiplatform.library`
- `com.android.lint`
- `org.jetbrains.kotlin.plugin.serialization`
- iOS targets (`iosArm64`, `iosSimulatorArm64`)

Exposes a `kmpLibrary { }` DSL for iOS framework production (`iosFrameworkName`, `enableSkie`). Framework production is restricted to `:libs:di` only — attempting to set `iosFrameworkName` in any other module fails the build. Reason: iOS can load at most one K/N framework per process.

### `yaseyo.android.library`
Pure Android libraries (typically `:feature:*:android`). Applies:
- `com.android.library` (includes lint)
- `org.jetbrains.kotlin.android`

Sets min/compile SDK from the version catalog, Java 11 source/target compatibility, and Kotlin JVM 11 target to match the rest of the project.

## Module structure patterns

### `:feature:<name>`
See `create-new-feature` skill. Structure:
- Parent (`yaseyo.kmp.library`) — no source, just `api(:api)` in commonMain
- `:api` (`yaseyo.kmp.library`) — required, commonMain public surface
- `:impl` (`yaseyo.kmp.library`) — optional
- `:android` (`yaseyo.android.library`) — pure Android, depends on `:impl` if exists else `:api`

### `:lib:<name>`
Parent (`yaseyo.kmp.library`) with commonMain aggregating `api(:api)` + `implementation(:impl)`. See `libs/auth` and `libs/user` for the pattern.

## Internal module boundary linter

`ModuleBoundaryEnforcement.kt` (invoked automatically by both convention plugins) enforces that `:impl` and `:android` modules are internal:

- **Allowed** to depend on `:feature:onboarding:impl` / `:feature:onboarding:android`:
  - Any module within the same feature tree (e.g. `:feature:onboarding` or `:feature:onboarding:android`)
  - `:libs:di` (DI composition root, whitelisted)
- **Not allowed**: any other module. The build fails with a "DESIGN BREACH" message showing the exact line to change.

To extend the rule (e.g. add another internal suffix, or whitelist another module), edit:
`build-logic/convention/src/main/kotlin/dev/yaseyo/ModuleBoundaryEnforcement.kt`

## iOS framework export (for Swift consumption)

In `libs/di/build.gradle.kts`, inside the iOS target block:
```kotlin
export(projects.feature.<name>)  // always the parent, never :api/:impl/:android
```

And `api(projects.feature.<name>)` in commonMain dependencies so that the exported types are visible at compile time.
