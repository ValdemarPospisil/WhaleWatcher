# Implementation Plan: Pre-made Categories and Database Documentation

## Phase 1: System Categories Documentation [checkpoint: c999762]
- [x] Task: Create `docs/architecture/system-categories.md` [d541f62]
    - [x] Create the `docs/architecture` directory.
    - [x] Draft the document detailing the 5 chosen categories (Databases, DevOps & CI/CD, Media Server, Web Servers, Operating Systems).
    - [x] Explain that these are pre-filled, read-only lists provided by the app.
- [x] Task: Conductor - User Manual Verification 'Phase 1: System Categories Documentation' (Protocol in workflow.md)

## Phase 2: Database Schema Documentation [checkpoint: 9580e99]
- [x] Task: Create `docs/architecture/database-schema.md` [fe341ef]
    - [x] Draft the document introducing the local Room database architecture.
    - [x] Detail the `DockerImage` table, specifying the saved fields (Name, Namespace, Description, Star Count, Pull Count) and explaining why only these are saved locally.
    - [x] Detail the unified `List` table and the `type` column logic.
    - [x] Detail the `ListImageCrossReference` table.
    - [x] Explicitly state constraints regarding read-only System Categories and the un-deletable Favorites list.
- [x] Task: Conductor - User Manual Verification 'Phase 2: Database Schema Documentation' (Protocol in workflow.md)

## Phase 3: ER Diagram Generation
- [ ] Task: Add Mermaid ER diagram to database schema doc
    - [ ] Create a Mermaid.js block within `docs/architecture/database-schema.md`.
    - [ ] Map out the `DockerImage`, `List`, and cross-reference entities with their relationships.
    - [ ] Ensure formatting renders correctly in standard Markdown viewers.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: ER Diagram Generation' (Protocol in workflow.md)