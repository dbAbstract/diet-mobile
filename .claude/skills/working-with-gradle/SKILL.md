---
name: working-with-gradle
description: This skill is to instruct you how to go about configuring a new gradle module or altering the gradle configuration of this project by adding/removing/updating dependencies.
---

# How to teach me or explain to me

## Instructions

- When adding a new KMP module, use the convention plugin/s defined in the build-logic module
- When adding a plugin or configuring a block in build.gradle.kts, consider adding it into either an
  existing convention plugin or suggesting the creation of a new one.
- When adding a plugin, don't forget to add the plugin to the project level build.gradle.kts too!

## Parent proxy module pattern

Every `:feature:<name>` and `:lib:<name>` group uses a parent proxy module. The parent:
- Has its own `build.gradle.kts` with **no namespace** (it is not an Android library itself, just a KMP aggregator)
- Declares `api(projects.feature.<name>.api)` so consumers get the public API transitively
- Declares `implementation(projects.feature.<name>.impl)` to pull in the concrete implementations
- Hosts the aggregated Koin module (see `wiring-up-koin` skill)

Consumers (e.g. `:libs:di`, `:composeApp`) depend **only** on the parent — never on `:api` or `:impl` directly. This keeps the dependency graph clean and prevents leaking implementation details.

Example parent `build.gradle.kts`:
```kotlin
plugins {
    id("yaseyo.kmp.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.feature.<name>.api)
            implementation(projects.feature.<name>.impl)
        }
    }
}
```

## Examples