# Specification: Favorite System

## Overview
Implement a persistent favorite system allowing users to save Docker images to a dedicated local list. This enhances discovery by letting users quickly bookmark images from search results or detail screens.

## Functional Requirements
1.  **Favorite Toggle**:
    *   Add a heart icon toggle to each search result card in the **Search Screen**.
    *   Add a heart icon toggle to the Top App Bar of the **Image Detail Screen**.
2.  **Visual Feedback**:
    *   A "favorited" state shows a filled red heart.
    *   An "unfavorited" state shows a hollow/outlined heart.
    *   Display a Snackbar notification when an item is added to or removed from Favorites (e.g., "Added to Favorites").
3.  **Persistence (Unified List System)**:
    *   Utilize the existing `DockerImageEntity` or a linking table within the `AppDatabase`.
    *   Implement a reserved list type `FAVORITE` as per the Product Definition's "Unified List System" note.
    *   Ensure the "Favorites" section on the **Home Screen** and **Library Screen** automatically updates when the favorite status changes.
4.  **Removal**:
    *   Toggling the heart icon to the "off" state removes the image from the Favorites list.

## Non-Functional Requirements
*   **Offline First**: Favorite status must be saved locally and remain accessible without an internet connection.
*   **Performance**: Toggling favorites should be instantaneous in the UI, with database operations handled asynchronously via Coroutines/Flow.

## Acceptance Criteria
*   [ ] User can click a heart on a search result to favorite an image.
*   [ ] User can click a heart on the Image Detail screen to favorite an image.
*   [ ] The heart icon color/fill changes immediately upon clicking.
*   [ ] A Snackbar appears confirming the action.
*   [ ] Favorited images appear in the "Favorites" section on the Home and Library screens.
*   [ ] Untoggling the heart removes the image from the "Favorites" section.
*   [ ] Data persists across app restarts.

## Out of Scope
*   Cloud synchronization of favorites.
*   Sharing favorites with other users.
*   Custom list management (this is a separate system, though it may share the same underlying DB logic).