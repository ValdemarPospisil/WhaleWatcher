# Specification: Main UI Scaffold and Theming

## Overview
This track focuses on scaffolding the main UI of the WhaleWatcher app using Jetpack Compose. It includes setting up the core navigation structure with a Bottom Navigation Bar and creating the foundational layouts for the primary screens: Home, Search, and Library, as well as a generic List Details page. The UI will implement a "Deep Ocean" (Dark blue background with teal accents) color palette. To visualize the layout, the screens will be populated with hardcoded dummy data representing Docker images and System Categories.

## Functional Requirements
- **Theme & Styling**:
  - Implement a "Deep Ocean" Material Theme (Dark blue surfaces, Teal accents).
  - Define custom Typography and Shapes if necessary for the dark aesthetic.
- **Navigation Architecture**:
  - Setup `NavHost` using Jetpack Navigation Compose.
  - Implement a `Scaffold` with a `BottomNavigationBar` containing three tabs: Home, Search, and Library.
- **Primary Screens**:
  - **Home (Dashboard)**: A scrolling view showing horizontally scrolling rows of dummy images categorized by predefined System Categories (e.g., "Databases", "DevOps & CI/CD").
  - **Search**: A page with a disabled search bar at the top and an empty state or dummy list below.
  - **Library**: A vertical list showing "Favorites" and dummy Custom Lists.
- **Secondary Screens**:
  - **List Details**: A generic screen showing a vertical list of dummy images, accessible when a user clicks "View All" on a category or a custom list.
- **Data Layer (Dummy Data)**:
  - Create static data models (e.g., `DummyImage`, `DummyCategory`) within the UI or presentation layer to render the components. Star count and pull count will be mocked.

## Non-Functional Requirements
- All UI must be written using Jetpack Compose.
- The UI should handle different screen sizes gracefully (responsive design).
- Navigation should handle state correctly (e.g., preserving back stack).

## Acceptance Criteria
- [ ] Jetpack Compose dependencies are configured in `build.gradle.kts` (if not already present).
- [ ] A "Deep Ocean" Compose Theme is defined and applied to the main Activity.
- [ ] Bottom Navigation works, switching between Home, Search, and Library screens.
- [ ] Home screen displays at least two sections of horizontally scrolling dummy images.
- [ ] Library screen displays a list of dummy collections.
- [ ] Clicking a list/category navigates to the generic List Details screen displaying vertical image cards.

## Out of Scope
- Actual API integration or Room Database logic.
- The Image Details Screen (will be handled in a separate track).
- Functional search (typing in the search bar won't filter yet).