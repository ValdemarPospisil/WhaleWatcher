# Implementation Plan: Predefined System Categories

## Phase 1: Data Layer Updates & Pre-population
- [x] Task: Update Category/List Room Entity (TDD) 46e5415
    - [x] Sub-task: Write failing tests for category entity updates (description, iconName, isPredefined).
    - [x] Sub-task: Implement entity changes and Room database migrations.
- [x] Task: Create System Categories JSON Asset b55eaba
    - [x] Sub-task: Generate JSON file with 10 categories and images.
    - [x] Sub-task: Place in `assets/` directory.
- [x] Task: Implement Database Pre-population logic (TDD) ad76bb8
    - [x] Sub-task: Write failing tests for initial pre-population logic.
    - [x] Sub-task: Implement JSON parsing and database insertion on first launch.
- [x] Task: Conductor - User Manual Verification 'Phase 1: Data Layer Updates & Pre-population' (Protocol in workflow.md)

## Phase 2: Home Page UI Updates
- [x] Task: Update Home Page View Model (TDD) 1aaadd3
    - [x] Sub-task: Write failing tests for ViewModel fetching system categories.
    - [x] Sub-task: Implement ViewModel data flow from Repository.
- [x] Task: Implement Home Page Grid UI (TDD) 8d35e23
    - [x] Sub-task: Add `material-icons-extended` dependency if needed.
    - [x] Sub-task: Write Compose UI tests for the 2x5 grid of categories.
    - [x] Sub-task: Update `HomeScreen.kt` to use LazyVerticalGrid for system categories.
    - [x] Sub-task: Create CategoryItem Composable.
- [x] Task: Conductor - User Manual Verification 'Phase 2: Home Page UI Updates' (Protocol in workflow.md)

## Phase 3: List Detail Screen Updates
- [ ] Task: Update List Detail Screen UI (TDD)
    - [ ] Sub-task: Write Compose UI tests for header description visibility.
    - [ ] Sub-task: Implement header description UI and hide edit/delete actions for predefined categories.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: List Detail Screen Updates' (Protocol in workflow.md)
