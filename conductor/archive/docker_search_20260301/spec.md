# Specification: Docker Hub Image Search & Pagination

## Overview
Implement the "Search" tab functionality for WhaleWatcher to query the Docker Hub API for public images. The search will be debounced by 500ms to respect API rate limits and will feature infinite scrolling to load subsequent pages of results seamlessly. It will also display the total count of found images and handle loading/empty states.

## Functional Requirements
- **Search Input:** A text input field in the Search tab to query the Docker Hub API (`/v2/search/repositories/?query=...`).
- **Trigger:** The search API call must be triggered automatically after 500ms of typing inactivity (debounce mechanism).
- **Search Results:** Display a list of Docker images returned by the API (repo_name, short_description, star_count, pull_count).
- **Total Count:** Display the total number of found images (the `count` field from the API response) clearly in the UI.
- **Pagination (Infinite Scroll):** As the user scrolls near the end of the current result list, automatically fetch the next page using the `next` URL provided in the API response.
- **Loading State:** Display a visual indicator (e.g., spinner) while the initial search or subsequent pages are being fetched.
- **Empty State:** Display a clear message (e.g., "No results found") when the API returns an empty list for a query.

## Non-Functional Requirements
- **API Rate Limiting:** Strictly adhere to the 500ms debounce to prevent HTTP 429 Too Many Requests errors.
- **Performance:** Smooth scrolling performance with `LazyColumn` even when loading subsequent pages.

## Acceptance Criteria
- [ ] Typing in the search bar triggers an API call only after the user stops typing for 500ms.
- [ ] The search results are displayed in a scrollable list, showing relevant metadata.
- [ ] The total count of matching images is displayed above the results.
- [ ] Scrolling to the bottom of the list automatically fetches and appends the next page of results.
- [ ] A loading indicator is visible during API calls.
- [ ] An empty state message is shown if the search yields zero results.

## Out of Scope
- Detailed Image View (clicking a result opens the Detail View, but the Detail View implementation itself is out of scope if not already done).
- Complex filtering.