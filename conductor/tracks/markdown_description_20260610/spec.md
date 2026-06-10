# Specification: Markdown Descriptions in Image Details

## Overview
The "Description" field on the Image Detail page currently displays raw markdown, which is unformatted and difficult to read. This track implements markdown rendering for this field so that formatted text is presented. Additionally, a separator line will be added to clearly distinguish the "Description" header from the content itself. 

## Functional Requirements
1. **Markdown Rendering**: Render the `description` text in the Image Detail screen using the `jeziellago/compose-markdown` library.
2. **Heading Downscaling**: Provide custom styling rules to the markdown renderer so that HTML headings (H1-H6) are scaled down (e.g., H1 behaves like H3, H2 behaves like H4) to prevent massive text blocks on small mobile screens.
3. **Visual Separation**: Insert a Material `HorizontalDivider` directly below the "Description" header to separate the title from the markdown content.

## Non-Functional Requirements
- **Performance**: The markdown rendering should be performant enough to not cause stuttering during scroll interactions.
- **Maintainability**: The custom styling configuration should be easy to update if future tweaks are required.

## Acceptance Criteria
- [ ] Navigating to an Image Detail screen successfully displays its description as parsed markdown.
- [ ] Heading elements (#, ##, etc.) within the markdown text appear smaller than standard default HTML heading sizes.
- [ ] A visible horizontal line separates the word "Description" from the rendered markdown text below it.

## Out of Scope
- Complex HTML or non-standard markdown extensions (if not natively supported by the chosen library).
- Rendering markdown in any screens other than the Image Detail screen.
