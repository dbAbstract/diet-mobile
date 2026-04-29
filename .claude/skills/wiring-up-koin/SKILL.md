---
name: wiring-up-koin
description: This skill is to instruct you how to go about setting up the wiring of Koin
---

# How to wire up Koin

## Composition-root model

`:libs:di` is the single composition root for Dependency Injection. It:
- Assembles all Koin modules in `AppModules.kt` (commonMain) and `KoinInitializer.kt` (androidMain)
- Is the **only** module outside of a feature tree allowed to reference `:android` / `:impl` directly (enforced by the `yaseyo.kmp.library` / `yaseyo.android.library` convention plugin linter)
- No aggregator/umbrella Koin module per feature. `:libs:di` references the common and platform-specific Koin modules directly.

## Module ownership per module type

### `:feature:<name>:api` (KMP, commonMain)
```kotlin
val <name>Module = module {
    factoryOf(::SomeUseCase)
    // platform-agnostic bindings only
}
```

### `:feature:<name>:impl` (KMP, commonMain) — optional
If an `:impl` exists, it hosts bindings for implementations that should not leak via `:api`. Define a separate `val <name>ImplModule = module { ... }` if needed.

### `:feature:<name>:android` (pure Android library)
```kotlin
val <name>FeatureAndroidModule = module {
    factory<FeatureNavigation> { <Name>FeatureNavigation() }
    // Android-specific bindings: ViewModels, platform services, etc.
}
```

### `:libs:di`
`AppModules.kt` (commonMain):
```kotlin
internal val appModules = listOf(
    // ...
    <name>Module,  // from :api
)
```

`KoinInitializer.kt` (androidMain):
```kotlin
startKoin {
    androidContext(context)
    modules(appModules + navigationAndroidModule + <name>FeatureAndroidModule)
}
```

## Scope rules

- Register definitions in the **root scope** (`module { factory<T> { ... } }`) unless there is a specific reason to scope them.
- Do **not** wrap definitions in `activityRetainedScope { }` unless the definition must be tied to an activity's retained lifecycle. Scoped definitions are invisible to root-scope lookups like `getKoin().getAll<T>()`.

## Multi-binding with `getAll`

When multiple `:android` modules contribute implementations of the same interface (e.g. `FeatureNavigation`), each registers its own `factory<TheInterface>`. Call `getKoin().getAll<TheInterface>()` at the call site to collect all of them. This is how `App.kt` aggregates nav entries across features.

## What NOT to do

- **Don't create a per-feature aggregator Koin module** in the parent. The parent has no source. Aggregation happens at the `:libs:di` composition root.
- **Don't have the parent depend on `:android`** — it would create a project cycle (`:android` depends on `:api`/`:impl`, which live under parent).
- **Don't include `:api`'s Koin module from inside `:android`'s module**. `:libs:di` registers both separately. Using `includes(...)` double-registers.
