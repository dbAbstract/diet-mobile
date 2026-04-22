---
name: create-new-feature
description: This skill is to instruct you how to go about implementing a new feature
---

# How to create a new feature

## Module structure rules
- Multi-module KMP project. No single `:shared` module.
- `:lib` modules (e.g. `:lib:auth`) — core functionality or I/O. Usually `:api` and `:impl` children, plus a parent proxy module unless too simple to warrant it.
- `:feature` modules (e.g. `:feature:onboarding`) — user-facing features. Usually `:api` and `:impl` children, plus a parent proxy module.
  - `:feature:<name>:api` — routes, use case interfaces, domain models, common Koin module. Lives in `commonMain`.
  - `:feature:<name>:impl` — concrete implementations and platform-specific code. Android nav wiring lives in `androidMain`.
  - `:feature:<name>` (parent) — umbrella/proxy. Depends on `:api` (via `api(...)`) and `:impl`. Owns the aggregated Koin module that combines both. **This is what `:libs:di` and other consumers depend on — never `:api` or `:impl` directly.**
- Always get the user's architectural approval before each step.
- After any new feature or tech stack choice lands, update the project README.

---

## Step-by-step: wiring a new feature into navigation

### 1. Define routes in `:feature:<name>:api`
File: `feature/<name>/api/src/commonMain/kotlin/dev/yaseyo/<name>/api/navigation/<Name>Routes.kt`

```kotlin
sealed interface <Name>Routes : AppRoute {
    data object <Screen> : <Name>Routes
    // add more screens as needed
}
```

### 2. Create the `FeatureNavigation` impl in `:feature:<name>:impl`
File: `feature/<name>/impl/src/androidMain/kotlin/dev/yaseyo/<name>/impl/<Name>FeatureNavigation.kt`

```kotlin
internal class <Name>FeatureNavigation : FeatureNavigation {
    override val navEntryProvider: EntryProviderScope<AppRoute>.() -> Unit = {
        entry<<Name>Routes.<Screen>> {
            // Composable UI goes here
        }
    }
}
```

### 3. Create the Koin android module in `:feature:<name>:impl`
File: `feature/<name>/impl/src/androidMain/kotlin/dev/yaseyo/<name>/impl/<Name>FeatureAndroidModule.kt`

```kotlin
val <name>FeatureAndroidModule = module {
    factory<FeatureNavigation> { <Name>FeatureNavigation() }
}
```

Key rule: **do not wrap in `activityRetainedScope`**. Register directly in the root scope so `getKoin().getAll<FeatureNavigation>()` can collect it.

### 4. Create the parent proxy module's aggregated Koin module
File: `feature/<name>/src/androidMain/kotlin/dev/yaseyo/<name>/<Name>Module.kt`

This is the single public entry point for all of this feature's DI. It includes both the `:api` and `:impl` modules:

```kotlin
val <name>Module = module {
    includes(<name>ApiModule, <name>FeatureAndroidModule)
}
```

The parent's `build.gradle.kts` must depend on both children:
```kotlin
commonMain.dependencies {
    api(projects.feature.<name>.api)        // transitive — exposes the public API
    implementation(projects.feature.<name>.impl)
}
```

### 5. Register the parent module in `KoinInitializer`
File: `libs/di/src/androidMain/kotlin/dev/yaseyo/di/KoinInitializer.kt`

```kotlin
modules(appModules + navigationAndroidModule + <name>Module)
```

`:libs:di` must only depend on `projects.feature.<name>` (the parent) — never on `:api` or `:impl` directly.

---

## How `App.kt` collects all features (do not change this)

`App.kt` uses `getKoin().getAll<FeatureNavigation>()` to collect every registered `FeatureNavigation` instance, then wires them all into the Nav3 `entryProvider` via the `registerAll()` extension:

```kotlin
val allFeatureNavigation = getKoin().getAll<FeatureNavigation>()

NavDisplay(
    ...
    entryProvider = allFeatureNavigation.registerAll(),
)

private fun List<FeatureNavigation>.registerAll(): (AppRoute) -> NavEntry<AppRoute> =
    entryProvider {
        forEach { it.navEntryProvider(this) }
    }
```

Each feature only needs to register its `factory<FeatureNavigation>` — `App.kt` picks it up automatically.

---

## Checklist for a new feature

- [ ] Routes defined in `:api` as a `sealed interface` extending `AppRoute`
- [ ] `<Name>FeatureNavigation` created in `:impl/androidMain`
- [ ] `:impl` Koin module registering `factory<FeatureNavigation>` in root scope
- [ ] Parent proxy module created with aggregated Koin module
- [ ] `:libs:di` updated to depend on the parent (not `:api`/`:impl`) and include the aggregated module
- [ ] User approved architecture at each step
- [ ] README updated
