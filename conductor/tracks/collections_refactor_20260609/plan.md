# Implementation Plan: Collections Management and Favorites Refactoring

## Phase 1: Setup, Gradle Config, and Refactoring [checkpoint: 4ebf533]
- [x] Task: Configure Gradle Heap Size and Dependencies 108e89e
    - [x] Add `org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m` to `gradle.properties`.
    - [x] Add `implementation("androidx.compose.material:material-icons-extended")` to `app/build.gradle.kts`.
- [x] Task: Refactor "Library" to "Collections" 108e89e
    - [x] Rename `LibraryScreen.kt` to `CollectionsScreen.kt` and refactor class name.
    - [x] Update Navigation routes and Bottom Navigation labels to use "Collections" instead of "Library".
    - [x] Rename packages, directories, and other code symbols as necessary.
- [x] Task: Conductor - User Manual Verification 'Phase 1: Setup, Gradle Config, and Refactoring' (Protocol in workflow.md) 4ebf533

## Phase 2: Database Schema & Pre-population [checkpoint: 7a0dabb]
- [x] Task: Write Database Entity & DAO Unit Tests (Red) f21ccba
    - [x] Write tests verifying database pre-population.
    - [x] Write tests for inserting, updating, and deleting collections.
    - [x] Write tests for mapping images to collections.
- [x] Task: Define Collections Entities and DAOs (Green) f21ccba
    - [x] Create `CollectionEntity` with fields for name, description, iconName, isSystem, isFavorite.
    - [x] Create `CollectionImageCrossRef` for many-to-many relationship.
    - [x] Create `CollectionDao` interface.
    - [x] Add new entities to `AppDatabase` and update database version.
- [x] Task: Implement Database Pre-population (Green) f21ccba
    - [x] Implement Room database callback to pre-populate System Categories and Favorites collection on first launch.
- [x] Task: Run and Refactor Database Layer (Refactor) f21ccba
    - [x] Verify database unit tests pass.
- [x] Task: Conductor - User Manual Verification 'Phase 2: Database Schema & Pre-population' (Protocol in workflow.md) 7a0dabb

## Phase 3: Repository and ViewModel Integration [checkpoint: 752a161]
- [x] Task: Write Repository & ViewModel Unit Tests (Red) 4085baa
    - [x] Write tests for observing collections, favorites, and list contents.
    - [x] Write tests for toggling favorites and saving images to collections.
- [x] Task: Implement Collections & Favorites Repository Logic (Green) 4085baa
    - [x] Add collection management functions (fetch, create, rename, delete) to the repository layer.
    - [x] Add cross-reference mapping functions for adding/removing images in collections.
- [x] Task: Update ViewModel to Observe and Manage Collections (Green) 4085baa
    - [x] Update `MainViewModel` or create a new `CollectionsViewModel` to observe collections and categories.
    - [x] Update `ImageDetailViewModel` to expose favorite status and handle list/collection selections.
- [x] Task: Run and Refactor Integration Layer (Refactor) 4085baa
    - [x] Verify Repository and ViewModel unit tests pass.
- [x] Task: Conductor - User Manual Verification 'Phase 3: Repository and ViewModel Integration' (Protocol in workflow.md) 752a161

## Phase 4: UI Implementation & Wiring
- [x] Task: Write UI & Navigation tests (Red)
    - [x] Write Compose tests for toggling favorites, creating collections, and the save bottom sheet.
- [x] Task: Connect ImageDetailScreen Favorites (Green)
    - [x] Implement `ImageDetailScreen` interactions with `ImageDetailViewModel.toggleFavorite`.
    - [x] Ensure the heart icon changes color/state dynamically based on favorite status.
- [x] Task: Implement Add to Collection Bottom Sheet (Green)
    - [x] Create `AddToCollectionBottomSheet` component.
    - [x] Connect it to `CollectionsViewModel` to list collections and `ImageDetailViewModel` to save.
- [x] Task: Wire CollectionsScreen (Green)
    - [x] Implement UI for `CollectionsScreen` to observe collections and navigate into specific collections.
- [x] Task: Run and Refactor UI Layer (Refactor)
    - [x] Verify UI tests and UI components look correct.
- [ ] Task: Conductor - User Manual Verification 'Phase 4: UI Implementation & Wiring' (Protocol in workflow.md)
