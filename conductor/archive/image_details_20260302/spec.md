# Specification: Image Detail Screen

## Overview
Implement the Image Detail Screen to show comprehensive information about a specific Docker image, including its description, stats, and tags. This screen will be accessible from the Home, Search, and Library screens.

## Functional Requirements
- **Navigation**:
  - Image cards in Home, Search, and Library screens should be clickable and navigate to the Image Detail Screen.
  - The screen should have a Back button in the Top App Bar.
- **Content Structure (Tabs)**:
  - **Info Tab**:
    - Image Name and Namespace.
    - Prominent "Last Updated" date.
    - Secondary "Date Registered".
    - Stats: Star count and Pull count.
    - Description: Full description fetched from the API.
    - Link to official Docker Hub page (e.g., `https://hub.docker.com/r/namespace/repo`).
    - Dynamic Indicators: Show `is_private` or `status` (Active/Inactive) only if relevant.
    - "Save to List" button.
  - **Tags Tab**:
    - A collection of tags displayed as chips.
    - Each tag chip should show the tag name and size/last pushed date if feasible.
- **API Integration**:
  - Call `GET /v2/namespaces/{namespace}/repositories/{repository}` for image details.
  - Call `GET /v2/namespaces/{namespace}/repositories/{repository}/tags` for the list of tags.
- **Actions**:
  - "Save to List" opens a Bottom Sheet to select a destination list.
  - "Quick Favorite" icon in the Top App Bar.
  - Navigate to Docker Hub URL in a browser.

## Non-Functional Requirements
- **Performance**: Debounce API calls if necessary.
- **UI/UX**: Adhere to the WhaleWatcher design system and maintain consistency.

## Acceptance Criteria
- [ ] Users can navigate to the Image Detail Screen from any image card.
- [ ] The Info tab displays correct repository data (name, description, dates, stats).
- [ ] The Tags tab displays a list of tags as chips.
- [ ] The Docker Hub link correctly points to the image's repository.
- [ ] Indicators for `is_private` and `status` appear only when relevant.
- [ ] "Save to List" functionality is implemented.

## Out of Scope
- Implementing advanced image analysis or scanning.
- Pulling images to the device (Docker Hub CLI behavior).
- Authentication (initially focused on public images).
