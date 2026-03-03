# Implementation Plan: Image Detail Screen

## Phase 1: API, Models & Repository [checkpoint: 49fafb8]
- [x] Task: Update `DockerHubApi.kt` and define Models eb6a6ec
    - [ ] Add `GetRepository` and `ListRepositoryTags` to `DockerHubApi.kt`.
    - [ ] Define `RepositoryInfo` and `TagResponse` data classes.
- [x] Task: Update `DockerImageRepository` 6bb75b4
    - [ ] Write unit tests for `getImageDetails` and `getImageTags` in `DockerImageRepositoryTest`.
    - [ ] Implement `getImageDetails` and `getImageTags` in `DockerImageRepository`.
- [x] Task: Conductor - User Manual Verification 'API & Models' (Protocol in workflow.md) 49fafb8

## Phase 2: Navigation & Click Triggers [checkpoint: b9d079e]
- [x] Task: Navigation Configuration 53fcbdf
    - [ ] Add `ImageDetail` route to `Screen.kt`.
    - [ ] Register `ImageDetailScreen` in `MainActivity.kt`'s `NavHost`.
- [x] Task: Implement Navigation Triggers 863b0a5
    - [ ] Make `ImageCard` clickable and trigger navigation in `HomeScreen` and `LibraryScreen`.
    - [ ] Update `SearchScreen` result items to be clickable and navigate to details.
    - [ ] Write navigation verification tests for `SearchScreenTest`.
- [x] Task: Conductor - User Manual Verification 'Navigation' (Protocol in workflow.md) b9d079e

## Phase 3: Screen UI & Design Refinement [checkpoint: 3da9e32]
- [x] Task: `ImageDetailViewModel` Implementation 4834be7
    - [ ] Write unit tests for `ImageDetailViewModel` state management.
    - [ ] Implement `ImageDetailViewModel` to fetch and expose image details and tags.
- [x] Task: `ImageDetailScreen` UI 8bdbce4
    - [ ] Implement `ImageDetailScreen` using a `TabRow` for Info and Tags tabs.
    - [ ] Info tab: Display stats, dates (Last Updated prominent), and description.
    - [ ] Tags tab: Display tags as chips.
- [x] Task: Documentation Update d926658
    - [ ] Update `docs/design/overview.md` with the new Image Detail Screen design.
- [x] Task: Conductor - User Manual Verification 'Detail Screen' (Protocol in workflow.md) 3da9e32

## Phase: Review Fixes
- [x] Task: Apply review suggestions ce30330
