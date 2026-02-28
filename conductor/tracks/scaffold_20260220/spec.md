# Track: Project Scaffolding and Infrastructure Setup

## Overview
This track involves the initial setup of the WhaleWatcher Android project, including the core directory structure, initial build configuration, and organizational improvements (like moving API documentation).

## Functional Requirements
- **Directory Structure:** Create a standard Android/Hilt/Room structure with `app/src/main/kotlin/com/valdemar/whalewatcher/`, `data/`, and `di/` packages.
- **Build System Setup:** Initialize `settings.gradle.kts` and `app/build.gradle.kts` with the basic project settings and initial dependencies (Kotlin, Gradle, Hilt, Room).
- **API Documentation Organization:** Move the existing `api-docs.yaml` to the `docs/api/` directory.
- **Source Control Setup:** Create a comprehensive `.gitignore` for Android and add the `conductor/` directory to it.

## Non-Functional Requirements
- **Maintainability:** Ensure the directory structure follows standard Android development patterns for easier long-term management.
- **Minimalist POC:** Focus on creating the necessary folders and basic configurations rather than full feature implementations.

## Acceptance Criteria
- [ ] Directory structure (app, data, di) is created in the project root.
- [ ] `settings.gradle.kts` and `app/build.gradle.kts` are initialized and readable.
- [ ] `docs/api/api-docs.yaml` exists in the new location.
- [ ] `.gitignore` contains standard Android exclusions and the `conductor/` entry.

## Out of Scope
- Implementation of Docker API communication or persistence logic.
- Creation of any complex UI components beyond a simple `MainActivity` for verification.
