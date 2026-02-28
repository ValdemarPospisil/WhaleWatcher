# Implementation Plan: Main UI Scaffold and Theming

## Phase 1: Setup and Theming [checkpoint: bf8ec5d]
- [x] Task: Configure Jetpack Compose and dependencies [425b342]
    - [x] Update `build.gradle.kts` (app level) to enable Compose and add necessary dependencies (Navigation Compose, Material 3, Tooling).
    - [x] Sync Gradle project.
- [x] Task: Implement "Deep Ocean" Theme [425b342]
    - [x] Create/Update `ui/theme/Color.kt` with Dark Blue and Teal palette.
    - [x] Create/Update `ui/theme/Theme.kt` to apply the color palette to `MaterialTheme`.
- [x] Task: Conductor - User Manual Verification 'Phase 1: Setup and Theming' (Protocol in workflow.md)

## Phase 2: Core Navigation Architecture
- [ ] Task: Create Bottom Navigation Bar
    - [ ] Define sealed class for `Screen` routes (Home, Search, Library, ListDetails).
    - [ ] Create `BottomNavigationBar` composable component.
- [ ] Task: Setup NavHost and Main Scaffold
    - [ ] Update `MainActivity.kt` to use a Compose `Scaffold`.
    - [ ] Implement `NavHost` within the `Scaffold` content area, wiring up the routes to placeholder screens.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Core Navigation Architecture' (Protocol in workflow.md)

## Phase 3: Screen Implementations with Dummy Data
- [ ] Task: Create Dummy Data Models
    - [ ] Create static models for `DockerImage` (namespace, name, stars, pulls) and `ImageCategory`.
    - [ ] Populate lists with dummy data based on the System Categories documentation.
- [ ] Task: Implement Home Screen
    - [ ] Create `HomeScreen` composable with a vertical scrolling column.
    - [ ] Implement horizontally scrolling rows for System Categories and Favorites.
- [ ] Task: Implement Search and Library Screens
    - [ ] Create `SearchScreen` with a disabled search bar UI.
    - [ ] Create `LibraryScreen` showing a vertical list of collections (Favorites, Custom Lists).
- [ ] Task: Implement List Details Screen
    - [ ] Create `ListDetailsScreen` composable taking a category/list name as an argument.
    - [ ] Display a vertical list of image cards.
    - [ ] Wire navigation from Home/Library to List Details.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Screen Implementations with Dummy Data' (Protocol in workflow.md)