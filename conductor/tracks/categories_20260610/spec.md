# Track Specification: Predefined System Categories

## Overview
Introduce predefined "System Categories" to the WhaleWatcher app. On first launch, the app will pre-populate the local database with 10 predefined, professional categories (e.g., Databases, Web Servers, CI/CD, Monitoring) using a JSON asset file. Each category will contain 5-20 handpicked images and a specific Material Design icon. The Home page will be updated to display these categories as a 2x5 grid of clickable squares, navigating to the existing List Details screen.

## Functional Requirements
1. **Database Pre-population**:
   - On first launch, read a JSON asset file containing the 10 system categories and their images.
   - Insert these categories into the Room database, marking them as read-only system categories.
   - Insert the associated images, linking them to their respective category.
2. **Data Model Updates**:
   - Update the existing List/Category data model (Room entity) to support:
     - `description`: A short text description.
     - `iconName`: A string identifier mapped to a Material Design icon.
     - `isPredefined`: A boolean flag to prevent user modification.
3. **Home Page UI Updates**:
   - Display system categories on the Home tab as a 2-column grid.
   - Each category card will display the category's Material Design icon and its name.
   - Clicking a card navigates to the List Detail Screen.
4. **List Detail Screen Updates**:
   - Display the short description in a dedicated header card below the Top App Bar for system categories.
   - Hide edit/delete actions for predefined categories.
5. **Initial Dataset Generation**:
   - Generate a JSON file containing 10 professional categories (Databases, Web Servers, CI/CD, Monitoring & Observability, Security, Analytics, Operating Systems, Message Brokers, Networking, Dev Tools).

## Non-Functional Requirements
- **Dependencies**: Add `androidx.compose.material:material-icons-extended` dependency if not present.

## Acceptance Criteria
- App pre-populates 10 categories on first run.
- Home page shows a 2-column grid of categories with correct icons.
- Clicking a category opens the list showing the correct images and header description.
- Predefined categories cannot be deleted or renamed.

## Out of Scope
- Syncing predefined categories with a remote server post-installation.
