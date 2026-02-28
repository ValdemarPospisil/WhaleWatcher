# WhaleWatcher

WhaleWatcher is a native Android application designed for searching, browsing, and managing Docker Hub images. It allows users to quickly discover public Docker images, view detailed metadata, and organize them into curated, custom local lists (like "Homelab", "Dev Tools", etc.) or save them to a persistent "Favorites" list for quick access. 

## Key Features
- **Global Search:** Find Docker images on Docker Hub using the public API.
- **Local Collections:** Save and organize images using a robust, offline-first Room database.
- **Unified List System:** Seamlessly manage both system-curated categories and custom user wishlists.
- **Offline First:** UI observes local database state to respect aggressive API rate limits from Docker Hub.

## Tech Stack
- **Language:** Kotlin
- **Build System:** Gradle (Kotlin DSL)
- **UI:** Programmatic UI / Jetpack Navigation Compose
- **Architecture:** MVVM, Hilt (Dagger), Room, Kotlin Coroutines, Flow

## Build Instructions

This project uses Gradle with Kotlin DSL. Here are some useful commands for building and testing the project.

**To build the project:**
```bash
./gradlew build
```

**To compile the debug APK:**
```bash
./gradlew assembleDebug
```

**To run the app on a connected device or emulator:**
```bash
./gradlew installDebug
```

**To run unit tests:**
```bash
./gradlew test
```

**To run code formatting/linting (if configured):**
```bash
./gradlew check
```

## Repository Structure

An overview of the main directories in this repository:

- `app/` - The main Android application module containing all UI, logic, and data layers.
  - `src/main/kotlin/.../whalewatcher/` - Application source code.
    - `data/` - Database entities, DAOs, repositories, and network API definitions.
    - `di/` - Hilt dependency injection modules.
    - `ui/` - Activities, ViewModels, and Compose UI navigation.
  - `src/test/` - Unit tests.
- `conductor/` - Conductor agent project management files (`product.md`, plans, specifications).
- `docs/` - Project documentation, including API specs and design documents.
- `gradle/` - Gradle wrapper and version catalogs.