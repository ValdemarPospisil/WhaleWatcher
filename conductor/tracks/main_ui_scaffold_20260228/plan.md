# Implementation Plan: Main UI Scaffold and Theming

## Phase 1: Setup and Theming [checkpoint: bf8ec5d]
- [x] Task: Configure Jetpack Compose and dependencies [425b342]
    - [x] Update `build.gradle.kts` (app level) to enable Compose and add necessary dependencies (Navigation Compose, Material 3, Tooling).
    - [x] Sync Gradle project.
- [x] Task: Implement "Deep Ocean" Theme [425b342]
    - [x] Create/Update `ui/theme/Color.kt` with Dark Blue and Teal palette.
    - [x] Create/Update `ui/theme/Theme.kt` to apply the color palette to `MaterialTheme`.
- [x] Task: Conductor - User Manual Verification 'Phase 1: Setup and Theming' (Protocol in workflow.md)

## Phase 2: Core Navigation Architecture [checkpoint: 79a64fb]
- [x] Task: Create Bottom Navigation Bar [f914a79]
    - [x] Define sealed class for `Screen` routes (Home, Search, Library, ListDetails).
    - [x] Create `BottomNavigationBar` composable component.
- [x] Task: Setup NavHost and Main Scaffold [f914a79]
    - [x] Update `MainActivity.kt` to use a Compose `Scaffold`.
    - [x] Implement `NavHost` within the `Scaffold` content area, wiring up the routes to placeholder screens.
- [x] Task: Conductor - User Manual Verification 'Phase 2: Core Navigation Architecture' (Protocol in workflow.md)

## Phase 3: Screen Implementations with Dummy Data
- [x] Task: Create Dummy Data Models [0f7e48b]
    - [x] Create static models for `DockerImage` (namespace, name, stars, pulls) and `ImageCategory`.
    - [x] Populate lists with dummy data based on the System Categories documentation.
- [x] Task: Implement Home Screen [a0fc411]
    - [x] Create `HomeScreen` composable with a vertical scrolling column.
    - [x] Implement horizontally scrolling rows for System Categories and Favorites.
- [x] Task: Implement Search and Library Screens [5203174]
    - [x] Create `SearchScreen` with a disabled search bar UI.
    - [x] Create `LibraryScreen` showing a vertical list of collections (Favorites, Custom Lists).
- [x] Task: Implement List Details Screen [6494dee]
    - [x] Create `ListDetailsScreen` composable taking a category/list name as an argument.
    - [x] Display a vertical list of image cards.
    - [x] Wire navigation from Home/Library to List Details.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Screen Implementations with Dummy Data' (Protocol in workflow.md)