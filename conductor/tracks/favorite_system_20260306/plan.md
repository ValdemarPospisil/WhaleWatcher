# Implementation Plan: Favorite System

## Phase 1: Data Layer & Persistence
**Goal:** Implement the underlying database structure for Favorites using the Unified List System.

- [ ] Task: Create `FavoriteImageEntity` or extend `DockerImageEntity` to support a `isFavorite` flag.
- [ ] Task: Update `ImageDao` with methods to Insert, Delete, and Query Favorite images (using Flow).
- [ ] Task: Update `AppDatabase` to include the new logic.
- [ ] Task: **TDD Phase 1**: Write unit tests for `ImageDao` Favorite operations.
- [ ] Task: Implement `ImageDao` Favorite methods and verify with tests.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Data Layer' (Protocol in workflow.md)

## Phase 2: Repository & ViewModel Integration
**Goal:** Expose Favorite operations through the Repository and ViewModel layers.

- [ ] Task: Update `DockerImageRepository` to include favorite/unfavorite operations.
- [ ] Task: **TDD Phase 2**: Write unit tests for `DockerImageRepository` favorite logic.
- [ ] Task: Update `SearchViewModel` to handle favorite/unfavorite events from the Search Screen.
- [ ] Task: Update `ImageDetailViewModel` to handle favorite/unfavorite events from the Detail Screen.
- [ ] Task: Update `MainViewModel` (or a dedicated `LibraryViewModel`) to expose the stream of Favorite images.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Repository & ViewModel' (Protocol in workflow.md)

## Phase 3: UI Implementation - Search & Detail
**Goal:** Add heart icons and toggling logic to the Search and Image Detail screens.

- [ ] Task: **TDD Phase 3 (UI)**: Write Compose UI tests for `SearchScreen` and `ImageDetailScreen` favorite toggles.
- [ ] Task: Update `ImageCard` (used in Search) to display and toggle the heart icon.
- [ ] Task: Update `ImageDetailScreen` to display and toggle the heart icon in the Top App Bar.
- [ ] Task: Implement Snackbar notifications for favorite/unfavorite actions using `Scaffold`.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: UI Implementation' (Protocol in workflow.md)

## Phase 4: Integration & Display
**Goal:** Ensure favorited items appear correctly on the Home and Library screens.

- [ ] Task: **TDD Phase 4 (Integration)**: Write integration tests verifying favorited items show up on the Home screen.
- [ ] Task: Update `HomeScreen` to display the "Favorites" section using real data from the database.
- [ ] Task: Update `LibraryScreen` to display the "Favorites" list at the top.
- [ ] Task: Conductor - User Manual Verification 'Phase 4: Integration' (Protocol in workflow.md)

## Phase 5: Final Verification & Refactoring
**Goal:** Ensure overall quality and adherence to guidelines.

- [ ] Task: Run full test suite, linting (`./gradlew lintDebug`), and verify coverage.
- [ ] Task: Final code review and refactoring for DRY principle and performance.
- [ ] Task: Conductor - User Manual Verification 'Phase 5: Final Quality Check' (Protocol in workflow.md)