# Implementation Plan: Collections Management and Favorites Refactoring

## Phase 1: Setup, Gradle Config, and Refactoring
- [ ] Task: Configure Gradle Heap Size and Dependencies
    - [ ] Add `org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m` to `gradle.properties`.
    - [ ] Add `implementation("androidx.compose.material:material-icons-extended")` to `app/build.gradle.kts`.
- [ ] Task: Refactor "Library" to "Collections"
    - [ ] Rename `LibraryScreen.kt` to `CollectionsScreen.kt` and refactor class name.
    - [ ] Update Navigation routes and Bottom Navigation labels to use "Collections" instead of "Library".
    - [ ] Rename packages, directories, and other code symbols as necessary.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Setup, Gradle Config, and Refactoring' (Protocol in workflow.md)

## Phase 2: Database Schema & Pre-population
- [ ] Task: Write Database Entity & DAO Unit Tests (Red)
    - [ ] Write tests verifying database pre-population.
    - [ ] Write tests for inserting, updating, and deleting collections.
    - [ ] Write tests for mapping images to collections.
- [ ] Task: Define Collections Entities and DAOs (Green)
    - [ ] Create `CollectionEntity` with fields for name, description, iconName, isSystem, isFavorite.
    - [ ] Create `CollectionImageCrossRef` for many-to-many relationship.
    - [ ] Create `CollectionDao` interface.
    - [ ] Add new entities to `AppDatabase` and update database version.
- [ ] Task: Implement Database Pre-population (Green)
    - [ ] Implement Room database callback to pre-populate System Categories and Favorites collection on first launch.
- [ ] Task: Run and Refactor Database Layer (Refactor)
    - [ ] Verify database unit tests pass.
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
