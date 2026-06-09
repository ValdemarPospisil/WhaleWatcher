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

## Phase 2: Database Schema & Pre-population
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
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Database Schema & Pre-population' (Protocol in workflow.md)

## Phase 3: Repository and ViewModel Integration
- [ ] Task: Write Repository & ViewModel Unit Tests (Red)
    - [ ] Write tests for observing collections, favorites, and list contents.
    - [ ] Write tests for toggling favorites and saving images to collections.
- [ ] Task: Implement Collections & Favorites Repository Logic (Green)
    - [ ] Add collection management functions (fetch, create, rename, delete) to the repository layer.
    - [ ] Add cross-reference mapping functions for adding/removing images in collections.
- [ ] Task: Implement ViewModels Integration (Green)
    - [ ] Update `MainViewModel` or create a new `CollectionsViewModel` to observe collections and categories.
    - [ ] Update `ImageDetailViewModel` to expose favorite status and handle list/collection selections.
- [ ] Task: Run and Refactor Repository & ViewModel Layer (Refactor)
    - [ ] Verify repository and ViewModel tests pass.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Repository and ViewModel Integration' (Protocol in workflow.md)

## Phase 4: UI Implementation & Wiring
- [ ] Task: Write UI & Navigation tests (Red)
    - [ ] Write Compose tests for toggling favorites, creating collections, and the save bottom sheet.
- [ ] Task: Implement Interactive Collections UI (Green)
    - [ ] Update `CollectionsScreen` with a custom list of user collections, a "+" FAB, and dialog to select from 12-20 Material icons.
    - [ ] Update `ListDetailsScreen` to load images from the database and show rename/delete options for custom collections.
- [ ] Task: Implement Favorites Toggle UI (Green)
    - [ ] Update `ImageCard` and `ImageDetailScreen` to display the filled/outlined Heart icon based on favorite status and handle clicks.
- [ ] Task: Implement "Save to List" Bottom Sheet (Green)
    - [ ] Create a bottom sheet in `ImageDetailScreen` with checkboxes for adding/removing the image to various collections/favorites.
- [ ] Task: Run and Refactor UI Layer (Refactor)
    - [ ] Verify all UI tests and build compilation pass.
- [ ] Task: Conductor - User Manual Verification 'Phase 4: UI Implementation & Wiring' (Protocol in workflow.md)
