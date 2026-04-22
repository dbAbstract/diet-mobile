---
name: wiring-up-koin
description: This skill is to instruct you how to go about setting up the wiring of Koin
---

# How to wire up Koin

## Module ownership rules

- `:lib` and `:feature` modules each have `:api`, `:impl`, and a **parent proxy** module.
- `:api` owns a Koin module for its own definitions (use case bindings, interfaces, etc.).
- `:impl` owns a Koin module for its concrete implementations and platform-specific registrations.
- The **parent** module owns an **aggregated Koin module** that `includes(...)` both `:api` and `:impl` modules. This is the only Koin module that consumers (e.g. `:libs:di`) ever reference.
- `:libs:di` collects all parent aggregated modules and passes them to `startKoin { modules(...) }`.
- Consumers must **never** depend on `:api` or `:impl` directly — always depend on the parent.

## Scope rules

- Register definitions in the **root scope** (`module { factory<T> { ... } }`) unless there is a specific reason to scope them.
- Do **not** wrap definitions in `activityRetainedScope { }` unless the definition must be tied to an activity's retained lifecycle. Scoped definitions are invisible to root-scope lookups like `getKoin().getAll<T>()`.

## Multi-binding with `getAll`

When multiple modules contribute implementations of the same interface (e.g. `FeatureNavigation`), each registers its own `factory<TheInterface>`. Call `getKoin().getAll<TheInterface>()` to collect all of them at the call site.

## Structure per module type

### `:feature:<name>:api` (commonMain)
```kotlin
val <name>ApiModule = module {
    // interfaces, use case factories, etc.
}
```

### `:feature:<name>:impl` (androidMain or commonMain)
```kotlin
val <name>FeatureAndroidModule = module {
    factory<FeatureNavigation> { <Name>FeatureNavigation() }
    // other impl-specific bindings
}
```

### `:feature:<name>` parent (androidMain)
```kotlin
val <name>Module = module {
    includes(<name>ApiModule, <name>FeatureAndroidModule)
}
```

### `:libs:di` `KoinInitializer`
```kotlin
startKoin {
    androidContext(context)
    modules(appModules + navigationAndroidModule + <name>Module + ...)
}
```
