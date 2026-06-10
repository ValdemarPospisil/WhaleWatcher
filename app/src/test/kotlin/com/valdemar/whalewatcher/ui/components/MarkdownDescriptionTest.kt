package com.valdemar.whalewatcher.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class MarkdownDescriptionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `scaleDownHeadings downscales H1 and H2 appropriately`() {
        val markdown = "# Heading 1\n## Heading 2\nSome description"
        val scaled = scaleDownHeadings(markdown)
        
        // H1 (#) becomes H3 (###)
        // H2 (##) becomes H4 (####)
        assert(scaled.contains("### Heading 1"))
        assert(scaled.contains("#### Heading 2"))
        assert(scaled.contains("Some description"))
    }
}
