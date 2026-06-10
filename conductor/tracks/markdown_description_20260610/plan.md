# Implementation Plan: Markdown Descriptions in Image Details

## Phase 1: Markdown Rendering Setup
- [x] Task: Add Dependencies 666cf69
    - [x] Sub-task: Add `jeziellago/compose-markdown` dependency to `app/build.gradle.kts`.
- [x] Task: Implement Markdown Component (TDD) 03e280e
    - [x] Sub-task: Write UI tests to verify a custom markdown component renders with smaller headings.
    - [x] Sub-task: Implement a custom Composable that uses `compose-markdown` with specific typography overrides (scaling down H1-H6).
- [~] Task: Conductor - User Manual Verification 'Phase 1: Markdown Rendering Setup' (Protocol in workflow.md)

## Phase 2: Image Detail UI Updates
- [ ] Task: Update Image Detail Screen (TDD)
    - [ ] Sub-task: Write UI tests to verify the horizontal divider and markdown rendering in the "Info" tab.
    - [ ] Sub-task: Modify `ImageDetailScreen.kt` to replace the raw text description with the new markdown component.
    - [ ] Sub-task: Add a Material `HorizontalDivider` below the "Description" section title.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Image Detail UI Updates' (Protocol in workflow.md)
