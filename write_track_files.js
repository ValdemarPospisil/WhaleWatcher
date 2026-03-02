const fs = require('fs');

const metadata = JSON.stringify({
  track_id: "api-poc_20260227",
  type: "feature",
  status: "new",
  created_at: new Date().toISOString(),
  updated_at: new Date().toISOString(),
  description: "I would like to implement first API to try it, some POC. the api points i want to use are documented in @docs/api/README.md . It can be simple button that calls the API and shows the results in some format, it can just show the name of the image. Ask anything"
}, null, 2);
fs.writeFileSync('conductor/tracks/api-poc_20260227/metadata.json', metadata);

const spec = `# Specification: API Proof of Concept

## Overview
Implement a Proof of Concept (POC) to verify connectivity with the Docker Hub API. This will involve creating a simple UI with a button that, when clicked, fetches a list of public repositories from a specific namespace (e.g., 'library') and displays the name of the first image returned.

## Functional Requirements
- **Trigger:** A button on the main screen to initiate the API call.
- **API Call:** Send a GET request to the `GET /v2/namespaces/{namespace}/repositories` endpoint. For this POC, a hardcoded namespace like 'library' can be used.
- **Success Display:** Upon a successful API response, extract the name of an image (e.g., the first one in the results array) and display it in a Text View on the screen.
- **Error Handling:** 
  - If the API call fails (network error, invalid response, etc.), display a user-friendly error message on the screen.
  - Log the detailed error information to the console (Logcat) for debugging purposes.

## Non-Functional Requirements
- Utilize the project's established networking stack (Retrofit, OkHttp, Kotlin Serialization).
- Handle the network request asynchronously using Kotlin Coroutines to avoid blocking the main UI thread.

## Acceptance Criteria
- [ ] A button is visible on the main screen.
- [ ] Tapping the button triggers a network request to the Docker Hub API.
- [ ] If successful, the screen updates to display an image name retrieved from the API.
- [ ] If unsuccessful, the screen updates to display an error message, and error details are visible in Logcat.

## Out of Scope
- Full implementation of all API endpoints.
- Pagination of API results.
- Complex UI/UX design (keep it minimal for the POC).
- Data persistence (saving results to the local database).`;
fs.writeFileSync('conductor/tracks/api-poc_20260227/spec.md', spec);

const plan = `# Implementation Plan: API Proof of Concept

## Phase 1: Setup Network Data Layer
- [ ] Task: Define API Data Models
    - [ ] Sub-task: Write failing tests: Parse sample JSON response into expected data objects.
    - [ ] Sub-task: Implement: Create `DockerRepositoryResponse` and related data classes with `@Serializable` annotations.
- [ ] Task: Define Retrofit API Interface
    - [ ] Sub-task: Write failing tests: Use MockWebServer to verify the request path (`/v2/namespaces/{namespace}/repositories`) and mock responses.
    - [ ] Sub-task: Implement: Define `DockerHubApi` interface with a `GET` method for the endpoint.
- [ ] Task: Implement Network Repository
    - [ ] Sub-task: Write failing tests: Mock the API interface and test the repository's success and error flow (e.g., returning a Kotlin `Result` or custom domain state).
    - [ ] Sub-task: Implement: Create a `DockerImageRepository` class that delegates to the API interface.
- [ ] Task: Conductor - User Manual Verification 'Setup Network Data Layer' (Protocol in workflow.md)

## Phase 2: UI Implementation
- [ ] Task: Implement ViewModel
    - [ ] Sub-task: Write failing tests: Verify `MainViewModel` state transitions (Loading, Success with image name, Error with message) upon triggering a fetch.
    - [ ] Sub-task: Implement: Create `MainViewModel` utilizing Kotlin Coroutines and Flow/LiveData to expose UI state.
- [ ] Task: Update UI Layout & Wire to ViewModel
    - [ ] Sub-task: Write failing tests: UI tests (e.g., using Robolectric or Espresso) to verify the Button click triggers a state change and updates the TextView, including error display.
    - [ ] Sub-task: Implement: Add a Button and a TextView to the main UI. Wire the button to the ViewModel's fetch method and observe the state to update the text and handle errors (logging and display).
- [ ] Task: Conductor - User Manual Verification 'UI Implementation' (Protocol in workflow.md)`;
fs.writeFileSync('conductor/tracks/api-poc_20260227/plan.md', plan);

const index = `# Track api-poc_20260227 Context

- [Specification](./spec.md)
- [Implementation Plan](./plan.md)
- [Metadata](./metadata.json)`;
fs.writeFileSync('conductor/tracks/api-poc_20260227/index.md', index);

const tracksEntry = `

---

- [ ] **Track: API Proof of Concept**
*Link: [./tracks/api-poc_20260227/](./tracks/api-poc_20260227/)*`;
fs.appendFileSync('conductor/tracks.md', tracksEntry);