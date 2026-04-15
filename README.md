# Yaseyo (痩せよ)

A personal diet app built with Kotlin Multiplatform. Native Android UI with Compose, native iOS UI
with SwiftUI, shared business logic.

---

## Tech Stack

| Layer         | Technology                                                                           |
|---------------|--------------------------------------------------------------------------------------|
| Language      | Kotlin 2.3.20                                                                        |
| Multiplatform | Kotlin Multiplatform + Compose Multiplatform 1.10.3                                  |
| Android UI    | Jetpack Compose                                                                      |
| iOS UI        | SwiftUI                                                                              |
| Auth          | Firebase Auth via [gitlive](https://github.com/GitLiveApp/firebase-kotlin-sdk) 2.4.0 |
| Networking    | Ktor 3.4.2                                                                           |
| DI            | Koin 4.2.1                                                                           |
| iOS interop   | SKIE 0.10.11                                                                         |
| Formatting    | Spotless + ktlint                                                                    |
| Git hooks     | Lefthook                                                                             |

---

## Architecture

Multi-module KMP project. No single shared module — functionality is split across typed modules:

```
:composeApp          → Android app entry point
:iosApp              → iOS app entry point (Xcode project)

:libs:auth           → Aggregator (api + impl)
:libs:auth:api       → AuthRepository interface, AuthState
:libs:auth:impl      → Firebase Auth implementation
:libs:network        → Ktor HttpClient, auth token injection
:libs:coroutines     → CoroutineDispatchers, shared CoroutineScope
:libs:di             → Koin composition root
```

**Module conventions:**

- `:libs` modules always have `:api` (public contract) and `:impl` (implementation) — consumers
  depend on the aggregator
- Future `:feature` modules will follow a `:ui` / `:data` / `:domain` split — UI lives in the native
  codebases
- Convention plugins in `:build-logic` handle shared Gradle config (`yaseyo.kmp.library`)

---

## Setup

### Prerequisites

- JDK 17
- Android Studio (latest stable)
- Xcode 15+
- [Homebrew](https://brew.sh)

### 1. Clone and run setup

```sh
git clone <repo-url>
cd yaseyo-mobile
./scripts/setup.sh
```

Or via Gradle if you prefer:

```sh
./gradlew setup
```

This installs Lefthook (if needed) and registers the git hooks for auto-formatting on commit.

### 2. Firebase config

These files are gitignored — get them from Firebase console or a teammate:

- **Android:** place `google-services.json` in `composeApp/`
- **iOS:** place `GoogleService-Info.plist` in `iosApp/iosApp/`

### 3. iOS — Firebase SDK

Open `iosApp/iosApp.xcodeproj` in Xcode and add the Firebase iOS SDK via SPM:

1. `File` → `Add Package Dependencies`
2. URL: `https://github.com/firebase/firebase-ios-sdk`
3. Add `FirebaseAuth` and `FirebaseCore` to the `iosApp` target

---

## Running

**Android**

```sh
./gradlew :composeApp:assembleDebug
```

**iOS**

Open `iosApp/iosApp.xcodeproj` in Xcode and run.
