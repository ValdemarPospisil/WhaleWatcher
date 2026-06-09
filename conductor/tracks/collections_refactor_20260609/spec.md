# Specification: Collections Management and Favorites Refactoring

## Overview
This track implements a fully functional, offline-first Collections (formerly Library) and Favorites system. The application will transition from using hardcoded dummy data for user collections to a persistent Room Database schema. On the first launch, the database will be pre-populated with default System Categories. Users will be able to mark images as favorites (updating the UI and DB), create new collections with custom names, descriptions, and a chosen icon from a set of 12-20 available icons, delete/edit collections, and add/remove images from collections using a bottom sheet in the Image Detail screen.

## Functional Requirements
1. **Database Schema Refactoring**:
   - Define a `CollectionEntity` representing a collection (Favorites, System Categories, and Custom Collections).
     - Fields: `id` (Long, PK AutoGenerate), `name` (String, Unique), `description` (String), `iconName` (String), `isSystem` (Boolean), `isFavorite` (Boolean).
   - Define a `CollectionImageCrossRef` for the many-to-many relationship between `collections` and `docker_images`.
   - Update `AppDatabase` to include these tables.
2. **First-Launch Pre-population**:
   - Pre-populate the database with the 3 default System Categories:
     - `Databases` (Icon: `Storage`, Description: "Relational, document, and key-value stores")
     - `DevOps & CI/CD` (Icon: `Build`, Description: "CI/CD, container registry, and orchestration tools")
     - `Media Server` (Icon: `PlayArrow`, Description: "Media management, playback, and downloading utilities")
   - Pre-populate the special `Favorites` collection (Icon: `Favorite`, Description: "Images you've marked as favorite").
   - Leave custom user collections and favorites empty on first install.
3. **UI Refactoring ("Library" to "Collections")**:
   - Rename the third bottom bar tab from "Library" to "Collections".
   - Rename all related files, classes, and routes (e.g., `LibraryScreen.kt` to `CollectionsScreen.kt`, and `Screen.Library` to `Screen.Collections`).
4. **Interactive Favorite System**:
   - Implement the Heart icon behavior on `ImageCard` and `ImageDetailScreen`. Clicking it must toggle the favorite state, updating the Room Database, and turning the heart red (or outline) dynamically.
5. **Interactive Collections**:
   - In `CollectionsScreen`, clicking the Floating Action Button (FAB) opens a dialog to create a new Collection.
     - Inputs: Name, Description, and Icon Selector (selection grid of 12-20 Material icons).
     - On creation, save to the database.
   - In `ListDetailsScreen`, if it's a Custom Collection, show options to delete or rename it.
   - In `ImageDetailScreen`, clicking the "Save to List" button opens a Bottom Sheet displaying all user Collections and the Favorites collection with checkboxes, allowing the user to add or remove the image from multiple collections simultaneously.
6. **Icons Refactoring**:
   - Configure Gradle to support `material-icons-extended` by increasing JVM heap size.
   - Use modern, outlined icons on the Image Details screen: `Icons.Outlined.Public` (Browser) and `Icons.Outlined.BookmarkAdd` / `Icons.Outlined.Bookmark` (Save).

## Non-Functional Requirements
- **Offline-First**: Ensure all collections and image listings within them are observed reactively from the database.
- **TDD (Test-Driven Development)**: Write unit tests for new Dao methods, ViewModel business logic, and UI state management.

## Acceptance Criteria
- [ ] Database schema successfully compiles with Hilt injection.
- [ ] System Categories and Favorites are pre-populated in the DB on first install, while custom collections start empty.
- [ ] Tab 3 is named "Collections", and all code occurrences of "Library" are refactored to "Collections".
- [ ] Toggling the heart icon on any card/detail page successfully adds/removes the image from Favorites (heart changes color).
- [ ] User can create, rename, and delete collections.
- [ ] Creating a collection allows picking an icon from a list of 12-20 Material icons.
- [ ] Saving an image to a collection via the Bottom Sheet works correctly.
- [ ] Build compiles and passes all tests.
