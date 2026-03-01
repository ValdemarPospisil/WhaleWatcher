# Technology Stack

## Core Platform
- **Language:** Kotlin
- **Build System:** Gradle with Kotlin DSL
- **SDK Versions:**
    - `compileSdk`: 34
    - `minSdk`: 26
    - `targetSdk`: 34

## UI & Architecture
- **Framework:** Programmatic UI (transitioning to Jetpack Compose)
- **Architecture Pattern:** MVVM (Model-View-ViewModel)
- **Dependency Injection:** Hilt (Dagger)
- **Lifecycle Management:** Jetpack ViewModel and Lifecycle-aware components

## Data & Persistence
- **Local Database:** Room Persistence Library (SQLite abstraction)
- **Local Cache:** In-memory repository caching for search results

## Networking
- **HTTP Client:** Retrofit
- **Network Stack:** OkHttp (with logging and error interceptors)
- **Serialization:** Kotlin Serialization (JSON parsing)

## Asynchrony & Concurrency
- **Concurrency:** Kotlin Coroutines
- **Reactive Streams:** Kotlin Flow (for database and network data streams)

## Testing
- **Unit Testing:** JUnit 4 / JUnit 5
- **Mocking:** MockK
- **UI Testing:** Jetpack Compose UI Testing (JUnit 4 rules)
- **Android Environment Mocking:** Robolectric
