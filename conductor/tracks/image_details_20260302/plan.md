# Implementation Plan: Image Detail Screen

## Phase 1: API, Models & Repository
- [x] Task: Update `DockerHubApi.kt` and define Models eb6a6ec
    - [ ] Add `GetRepository` and `ListRepositoryTags` to `DockerHubApi.kt`.
    - [ ] Define `RepositoryInfo` and `TagResponse` data classes.
- [ ] Task: Update `DockerImageRepository`
    - [ ] Write unit tests for `getImageDetails` and `getImageTags` in `DockerImageRepositoryTest`.
    - [ ] Implement `getImageDetails` and `getImageTags` in `DockerImageRepository`.
- [ ] Task: Conductor - User Manual Verification 'API & Models' (Protocol in workflow.md)

## Phase 2: Navigation & Click Triggers
- [ ] Task: Navigation Configuration
    - [ ] Add `ImageDetail` route to `Screen.kt`.
    - [ ] Register `ImageDetailScreen` in `MainActivity.kt`'s `NavHost`.
- [ ] Task: Implement Navigation Triggers
    - [ ] Make `ImageCard` clickable and trigger navigation in `HomeScreen` and `LibraryScreen`.
    - [ ] Update `SearchScreen` result items to be clickable and navigate to details.
    - [ ] Write navigation verification tests for `SearchScreenTest`.
- [ ] Task: Conductor - User Manual Verification 'Navigation' (Protocol in workflow.md)

## Phase 3: Screen UI & Design Refinement
- [ ] Task: `ImageDetailViewModel` Implementation
    - [ ] Write unit tests for `ImageDetailViewModel` state management.
    - [ ] Implement `ImageDetailViewModel` to fetch and expose image details and tags.
- [ ] Task: `ImageDetailScreen` UI
    - [ ] Implement `ImageDetailScreen` using a `TabRow` for Info and Tags tabs.
    - [ ] Info tab: Display stats, dates (Last Updated prominent), and description.
    - [ ] Tags tab: Display tags as chips.
- [ ] Task: Documentation Update
    - [ ] Update `docs/design/overview.md` with the new Image Detail Screen design.
- [ ] Task: Conductor - User Manual Verification 'Detail Screen' (Protocol in workflow.md)
