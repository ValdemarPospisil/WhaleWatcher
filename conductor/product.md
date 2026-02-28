# Initial Concept
WhaleWatcher: An Android app to search and browse Docker images, manage custom lists (e.g., "homelab", "server1"), and save favorites to a local database.

Requirements:
1. Root Project Name: WhaleWatcher
2. Application ID: com.valdemar.whalewatcher
3. SDK Versions: compileSdk 34, minSdk 26, targetSdk 34.
4. Language: Kotlin.
5. Build System: Gradle with Kotlin DSL.
6. Architecture: Minimal, programmatic UI (Jetpack Compose later).
7. Git: Standard Android .gitignore.
8. API: Docker Hub API (api-docs.yaml).
9. DevOps focus and future App Store release.

# Product Definition

## Target Audience
WhaleWatcher is designed for:
- DevOps students and beginners who are learning about container management and mobile development.
- Power users and homelab enthusiasts who manage personal server environments and need a mobile tool for image reference.
- Professional software engineers who require a quick, portable way to browse and search Docker images.

## Goals
- Provide a seamless and intuitive mobile interface for browsing and searching public Docker Hub images.
- Enable users to efficiently organize Docker images into custom, user-defined lists tailored to different environments (e.g., "homelab", "server1").
- Serve as a foundational project for learning and implementing modern Android DevOps practices and robust Kotlin development.

## Key Features
- **Docker Hub Search:** Search for public Docker images and view detailed metadata, including tags and basic image information.
- **Local Collections:** Save favorite images and organize them into custom, locally-stored lists for quick access.

## DevOps and Distribution
- **Automated CI/CD:** Implementation of automated pipelines (e.g., GitHub Actions) for linting, testing, and building the application.
- **Release Automation:** Automated release management and app signing to streamline future submissions to the App Store.

## API Integration
- **Public Image Access:** Fetching image details and tags from the Docker Hub API without requiring initial user authentication.
- **Search Capabilities:** Searching for images by keyword, with support for identifying official and popular images.
- **Robustness:** Handling API rate limiting and network error states gracefully to provide a reliable user experience.

## Core Terminology & Data Model
To maintain consistency across the codebase, use the following terminology:
- **Image**: A single Docker image from Docker Hub (e.g., `linuxserver/plex`).
- **List**: A unified data structure (Room DB entity) containing multiple Images.
- **Favorites**: A persistent, default system List where users save Images they like.
- **System Category**: A pre-defined, read-only List based on popular Docker namespaces (e.g., `linuxserver`, `library`, `prom`, `grafana`).
- **Custom List**: A user-created List with a custom name (e.g., "My Weekend Project").

*Architectural Note:* "Favorites", "System Categories", and "Custom Lists" should preferably share the same underlying database structure/logic, differing only by a type flag (e.g., `listType = SYSTEM | FAVORITE | CUSTOM`).

## Navigation Architecture
The app uses a single-activity architecture with Jetpack Navigation Compose. The primary navigation is a `BottomNavigationBar` with exactly 3 tabs:
1. **Home** (Icon: Home)
2. **Search** (Icon: Magnifying Glass / Search)
3. **Library** (Icon: Bookmark / Folder)

## Primary Screens (Bottom Navigation Tabs)
### Tab 1: Home (Dashboard)
**Purpose:** A discovery feed showing predefined System Categories and the user's Favorites.
- **Content:**
  - A section/row displaying the **Favorites** List (showing the latest saved images, or a shortcut to the List).
  - Multiple sections for **System Categories** (e.g., "Top from LinuxServer.io", "Official Images").
- **Behavior:** Clicking on "View All" next to a category, or clicking the category itself, navigates to the *List Detail Screen*. Clicking an image card navigates to the *Image Detail Screen*.

### Tab 2: Search (Global Discovery)
**Purpose:** Search the entire Docker Hub registry via API.
- **Content:**
  - A prominent Search Bar at the top.
  - An empty state ("Search for an image...").
  - A vertical `LazyColumn` of search results (Images) fetched from the API.
- **Behavior:**
  - Must implement a `500ms debounce` on the text input to prevent hitting API rate limits (HTTP 429).
  - Each result card must have a quick-action "Heart/Bookmark" icon to instantly save it to the **Favorites** List.

### Tab 3: Library (My Collections)
**Purpose:** Management of the user's saved content.
- **Content:**
  - Fixed at the very top: The **Favorites** List (visually distinct, as it is the most used).
  - Below Favorites: A vertical list or grid of **Custom Lists** created by the user.
  - A `FloatingActionButton` (FAB) with a "+" icon in the bottom right corner.
- **Behavior:**
  - Clicking the FAB opens a dialog to create a new Custom List (asking for a name).
  - Clicking any List navigates to the *List Detail Screen*.

## Secondary Screens (Detail Views)
### Screen A: List Detail Screen
**Purpose:** Displays all Images contained within a specific List (System Category, Favorites, or Custom List).
- **Content:**
  - Top App Bar with the List name as the title and a Back button.
  - If it's a Custom List, include an option (icon) to delete or rename the list.
  - A vertical `LazyColumn` of Image cards.
- **Data Source:** For Favorites and Custom Lists, data is fetched exclusively from the local Room Database (Offline support).

### Screen B: Image Detail Screen
**Purpose:** Shows full information about a specific Docker Image.
- **Content:**
  - Top App Bar with Back button.
  - Image Name (e.g., `louislam/uptime-kuma`) and Namespace.
  - Stats: Star count and Pull count.
  - "Docker Pull" command snippet with a "Copy to Clipboard" button.
  - Full description (fetched from API).
  - A prominent "Save to List" button.
- **Behavior:** Clicking "Save to List" opens a Bottom Sheet allowing the user to select which List(s) (Favorites or any Custom List) to add the image to. There should also be a button directly on the image to instantly make it a favorite.

## Core Architecture: The Unified List System & API Rate Limiting
1. **Unified Lists:** The local Room database must handle both "System Categories" (pre-defined namespaces like `linuxserver`, `library`, `prom`) and "User Custom Wishlists" using the same underlying database entity (e.g., `Wishlist` with a boolean flag `isPredefined`).
2. **Single Source of Truth (SSOT):** To respect Docker Hub's aggressive API rate limits, the app MUST be offline-first. The UI should exclusively observe the Room database (via Kotlin Flows). The Repository layer fetches from the Retrofit API and inserts/updates the Room database.
3. **Debouncing:** The global search bar must implement a 500ms debounce before hitting the `/v2/search/repositories/` endpoint to prevent HTTP 429 Too Many Requests errors.