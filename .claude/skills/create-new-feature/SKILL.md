---
name: create-new-feature
description: This skill is to instruct you how to go about implementing a new feature
---

# How to create a new feature

## Module structure rules

Multi-module KMP project. No single `:shared` module.

### `:feature` modules (e.g. `:feature:onboarding`)
User-facing features. Structure:

```
:feature:<name>              ← parent, thin proxy. No source. Just api(:api).
  :api                       ← REQUIRED. KMP. Public surface: routes, use case interfaces, domain models, api Koin module.
  :impl                      ← OPTIONAL. Only create when there's shared commonMain implementation to hide behind :api.
  :android                   ← pure Android library. Nav entries, ViewModels, Compose, FeatureNavigation impl, Android Koin module.
```

Rules:
- `:api` is mandatory.
- `:impl` is case-by-case — add only when there's actual shared Kotlin implementation that warrants hiding. Don't create empty shells.
- `:android` depends on `:impl` when one exists, otherwise depends on `:api` directly.
- Parent has **no source**. It only exists to re-export `:api` via `api(projects.feature.<name>.api)`, so consumers depending on the parent get `:api` transitively without needing to know about the internal split.
- iOS presentation lives in the Xcode/SwiftUI project — **not** as a Gradle module. iOS consumes the umbrella `:libs:di` framework for use cases.

### `:lib` modules (e.g. `:lib:auth`)
Core functionality or I/O. Structure is `:api` + `:impl` + parent aggregator (see existing `libs/auth` and `libs/user` for the pattern). Parent `api`-depends on `:api`, `implementation`-depends on `:impl`.

## Always get architectural approval before each step. After any new feature lands, update the project README.

---

## Step-by-step: adding a new `:feature:<name>`

### 1. Create `:feature:<name>:api`
File: `feature/<name>/api/build.gradle.kts` — applies `yaseyo.kmp.library`, namespace `dev.yaseyo.<name>.api`, commonMain deps for what the public API needs.

Place in `commonMain`:
- `dev.yaseyo.<name>.api.navigation.<Name>Routes` — `sealed interface <Name>Routes : AppRoute`
- `dev.yaseyo.<name>.api.model.*` — domain models
- `dev.yaseyo.<name>.api.*` — use case classes/interfaces
- `dev.yaseyo.<name>.api.di.<Name>Module.kt` — `val <name>Module = module { ... }` with bindings that are platform-agnostic

### 2. Create `:feature:<name>` (parent proxy)
File: `feature/<name>/build.gradle.kts`:
```kotlin
plugins {
    id("yaseyo.kmp.library")
}

kotlin {
    androidLibrary { namespace = "dev.yaseyo.<name>" }

    sourceSets {
        commonMain.dependencies {
            api(projects.feature.<name>.api)
        }
    }
}
```
No `src/` directory. Nothing else.

### 3. Create `:feature:<name>:android`
File: `feature/<name>/android/build.gradle.kts` — applies `yaseyo.android.library` (and `libs.plugins.composeCompiler` if the module uses Compose). Namespace `dev.yaseyo.<name>.android`. Depends on `projects.feature.<name>.api` (or `projects.feature.<name>.impl` if one exists).

Place in `src/main/kotlin/dev/yaseyo/<name>/android/`:
- `<Name>FeatureNavigation.kt` — `internal class <Name>FeatureNavigation : FeatureNavigation { ... }` with nav entries
- `<Name>FeatureAndroidModule.kt` — `val <name>FeatureAndroidModule = module { factory<FeatureNavigation> { <Name>FeatureNavigation() } }`

Do **not** wrap in `activityRetainedScope`. Register in the root scope (see `wiring-up-koin` skill).

### 4. Register modules in `settings.gradle.kts`
```kotlin
include(":feature:<name>")
include(":feature:<name>:api")
include(":feature:<name>:android")
// include(":feature:<name>:impl") only if :impl was created
```

### 5. Wire up in `:libs:di`
`:libs:di` is the DI composition root — the ONE module allowed to depend on `:android` from outside its feature tree.

In `libs/di/build.gradle.kts`:
```kotlin
commonMain.dependencies {
    api(projects.feature.<name>)  // transitive :api exposure
}
androidMain.dependencies {
    implementation(projects.feature.<name>.android)
}
```

In `libs/di/src/commonMain/.../AppModules.kt`:
```kotlin
import dev.yaseyo.<name>.api.di.<name>Module
// add to appModules list
```

In `libs/di/src/androidMain/.../KoinInitializer.kt`:
```kotlin
import dev.yaseyo.<name>.android.<name>FeatureAndroidModule
// add to startKoin { modules(...) } call
```

### 6. iOS framework export (if the feature is consumed from Swift)
In `libs/di/build.gradle.kts` iOS target block:
```kotlin
export(projects.feature.<name>)
```
Exports the parent, which transitively exposes `:api` types to Swift.

---

## How `App.kt` collects all Android nav entries (do not change this)

`App.kt` uses `getKoin().getAll<FeatureNavigation>()` to collect every `FeatureNavigation` binding, then wires them all into the Nav3 `entryProvider` via the `registerAll()` extension. Each `:android` module only needs to register its own `factory<FeatureNavigation>` — `App.kt` picks it up automatically.

---

## Checklist

- [ ] `:api` created (routes, use cases, common Koin module)
- [ ] Parent proxy created (no source, just `api(:api)`)
- [ ] `:android` created (nav impl, android Koin module) — depends on `:api` or `:impl`
- [ ] `:impl` added only if there's actual shared implementation to justify it
- [ ] `settings.gradle.kts` updated
- [ ] `:libs:di` wired up: commonMain imports `:api`'s module, androidMain imports `:android`'s module
- [ ] iOS export added if Swift consumption expected
- [ ] User approved architecture at each step
- [ ] README updated
