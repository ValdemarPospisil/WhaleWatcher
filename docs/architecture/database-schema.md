# Database Schema Architecture

## Overview
WhaleWatcher uses a local Room database to store user collections and predefined system categories. To respect Docker Hub's API rate limits and provide a fast, responsive user experience, the application operates with an offline-first approach for its core list-rendering functionality.

<!-- ER_DIAGRAM_PLACEHOLDER -->

## Tables

### 1. `DockerImage`
This table stores minimal but essential information about Docker images. The goal is not to mirror the entire Docker Hub API response, but rather to save just enough data to render list items independently without network calls. 

**Saved Fields:**
- `id` (Primary Key, Auto-increment)
- `name`: The image name (e.g., `plex`).
- `namespace`: The publisher namespace (e.g., `linuxserver`, or `library` for official images).
- `description`: A brief summary of the image's purpose.
- `starCount`: The number of stars on Docker Hub.
- `pullCount`: The total number of pulls.

**Rationale:** When a user views a list (e.g., "Favorites"), the UI reads exclusively from the local Room database via Kotlin Flows. It only fetches full details from the Docker Hub API when the user explicitly clicks into an individual *Image Detail Screen*.

### 2. `List`
This is a unified table that handles all collections within the app, whether they are predefined or user-created.

**Fields:**
- `id` (Primary Key, Auto-increment)
- `name`: The display name of the list (e.g., "Favorites", "My Homelab", "Databases").
- `type`: An Enum/String flag distinguishing the list's behavior. It can be:
  - `FAVORITE`: The persistent, un-deletable system list.
  - `SYSTEM_CATEGORY`: A pre-filled, read-only list provided out-of-the-box.
  - `CUSTOM`: A standard user-created list.

**Constraints:**
- Lists of type `SYSTEM_CATEGORY` are strictly read-only for the user. They cannot be modified or deleted.
- The `FAVORITE` list is persistent. It cannot be renamed or deleted by the user.

### 3. `ListImageCrossReference`
Because an image can belong to multiple lists (e.g., an image can be in "Databases" and also saved to "Favorites"), a standard many-to-many relationship table is required.

**Fields:**
- `listId` (Foreign Key pointing to `List.id`)
- `imageId` (Foreign Key pointing to `DockerImage.id`)

*(Primary Key is a composite of both `listId` and `imageId`)*.

This cross-reference table allows fast querying of all images associated with a specific list, and conversely, all lists that contain a specific image.