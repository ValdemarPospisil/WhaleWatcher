# Specification: Pre-made Categories and Database Documentation

## Overview
This track involves creating technical documentation for the WhaleWatcher app's database architecture and defining the initial set of pre-made "System Categories" (handpicked lists of Docker images). The goal is to provide a clear schema for the local Room database, emphasizing an offline-first approach where minimal but essential image data is stored locally, and detailing how the unified list system operates.

## Functional Requirements
- **System Categories Documentation**:
  - Create a Markdown document detailing the 5 pre-prepared System Categories: `Databases`, `DevOps & CI/CD`, `Media Server`, `Web Servers`, and `Operating Systems`.
  - Explain the purpose of these categories (read-only, pre-filled lists for users).
- **Database Schema Documentation**:
  - Create a Markdown document containing an ER (Entity-Relationship) diagram of the database.
  - Detail the `DockerImage` table, specifying that it will only store essential fields for list rendering: `Name`, `Namespace`, `Description`, `Star Count`, and `Pull Count`. All other details will be fetched via API when a user views the image details.
  - Detail the unified `List` table architecture. Explain how a single table will handle Favorites, System Categories, and Custom Lists using a `type` column or flag.
  - Detail the many-to-many relationship table (e.g., `ListImageCrossReference`) that links Images to Lists.
  - Explain the constraints: System Categories and their contents are read-only; the Favorites list cannot be deleted by the user.

## Non-Functional Requirements
- All documentation must be placed in the `docs/` directory (or appropriate subdirectories).
- The ER diagram should be represented using standard Markdown tools like Mermaid.js.
- Explanations of each table and column must be clear and concise.

## Acceptance Criteria
- [ ] `docs/architecture/system-categories.md` (or similar) is created with details on the 5 pre-made categories.
- [ ] `docs/architecture/database-schema.md` (or similar) is created.
- [ ] The database documentation includes a Mermaid ER diagram.
- [ ] The database documentation clearly explains the `DockerImage`, `List`, and cross-reference tables.
- [ ] The documentation explicitly lists the fields to be saved locally for an image (`Name`, `Namespace`, `Description`, `Star Count`, `Pull Count`).

## Out of Scope
- Implementing the Room database in Kotlin code.
- Populating the database with actual data in the app.
- Designing the UI for the categories.