# WhaleWatcher UI/UX Design & Architecture Overview

## Core Terminology & Data Model
- **Image**: A single Docker image from Docker Hub (e.g., `linuxserver/plex`).
- **List**: A unified data structure (Room DB entity) containing multiple Images.
- **Favorites**: A persistent, default system List where users save Images they like.
- **System Category**: A pre-defined, read-only List of handpicked images grouped by topic (e.g., `networking`, `dev tools`, `security`).
- **Custom List**: A user-created List with a custom name (e.g., "My Weekend Project").

## Navigation Architecture
- **Single-Activity Architecture** with Jetpack Navigation Compose.
- **BottomNavigationBar** with 3 tabs:
  1. **Home** (Icon: Home)
  2. **Search** (Icon: Magnifying Glass)
  3. **Library** (Icon: Bookmark)

---

## Primary Screens

### 1. Home (Dashboard)
A discovery feed showing predefined System Categories and the user's Favorites.

```text
+---------------------------------------+
| WhaleWatcher                          |
+---------------------------------------+
|                                       |
| [ Favorites ]              [View All] |
| +-------+ +-------+ +-------+         |
| | Image | | Image | | Image |         |
| |       | |       | |       |         |
| +-------+ +-------+ +-------+         |
|                                       |
| [ Networking ]             [View All] |
| +-------+ +-------+ +-------+         |
| | Image | | Image | | Image |         |
| |       | |       | |       |         |
| +-------+ +-------+ +-------+         |
|                                       |
| [ Dev Tools ]              [View All] |
| +-------+ +-------+ +-------+         |
| | Image | | Image | | Image |         |
| |       | |       | |       |         |
| +-------+ +-------+ +-------+         |
|                                       |
+---------------------------------------+
|   [Home]     [Search]     [Library]   |
+---------------------------------------+
```

### 2. Search (Global Discovery)
Search the entire Docker Hub registry via API.

```text
+---------------------------------------+
| [ Search for an image...          ] X |
+---------------------------------------+
|                                       |
|  +---------------------------------+  |
|  | linuxserver/plex           [<3] |  |
|  | Media server container          |  |
|  | ★ 1.2k   ↓ 500M                 |  |
|  +---------------------------------+  |
|                                       |
|  +---------------------------------+  |
|  | ubuntu                     [♡ ] |  |
|  | Official Ubuntu base image      |  |
|  | ★ 10k    ↓ 1B+                  |  |
|  +---------------------------------+  |
|                                       |
|                                       |
|                                       |
+---------------------------------------+
|   [Home]     [Search]     [Library]   |
+---------------------------------------+
```
*(Search includes a 500ms debounce to prevent API rate limiting).*

### 3. Library (My Collections)
Management of the user's saved content.

```text
+---------------------------------------+
| Library                               |
+---------------------------------------+
|                                       |
|  ★ Favorites                          |
|  -----------------------------------  |
|                                       |
|  📁 My Weekend Project                |
|                                       |
|  📁 Homelab                           |
|                                       |
|  📁 Work Tools                        |
|                                       |
|                                       |
|                                       |
|                                  (+)  |
+---------------------------------------+
|   [Home]     [Search]     [Library]   |
+---------------------------------------+
```
*(Floating Action Button (+) opens a dialog to create a new Custom List).*

---

## Secondary Screens

### Screen A: List Detail Screen
Displays all Images contained within a specific List.

```text
+---------------------------------------+
| [<-]  My Weekend Project     [Rename] |
|                              [Delete] |
+---------------------------------------+
|                                       |
|  +---------------------------------+  |
|  | louislam/uptime-kuma            |  |
|  | A fancy self-hosted monitoring  |  |
|  | tool                            |  |
|  +---------------------------------+  |
|                                       |
|  +---------------------------------+  |
|  | portainer/portainer-ce          |  |
|  | Docker UI management            |  |
|  +---------------------------------+  |
|                                       |
|                                       |
+---------------------------------------+
```

### Screen B: Image Detail Screen
**Purpose:** Shows full information about a specific Docker Image with tabs for Info and Tags.

```text
+---------------------------------------+
| [<-]  louislam/uptime-kuma       [<3] |
+---------------------------------------+
|   [ Info ]            [ Tags ]        |
+---------------------------------------+
|                                       |
|  [Private] [Active]                   |
|                                       |
|  Last Updated                         |
|  Oct 24, 2023                         |
|                                       |
|  Registered: Jan 15, 2021             |
|                                       |
|  Pulls                 Stars          |
|  50M                   5.4k           |
|                                       |
|  Description                          |
|  Uptime Kuma is an easy-to-use self-  |
|  hosted monitoring tool.              |
|                                       |
|  [        Save to List...        ]    |
|  [      View on Docker Hub       ]    |
+---------------------------------------+
```
*(The "Tags" tab displays a grid of available tags with their sizes. "Save to List" opens a bottom sheet.)*