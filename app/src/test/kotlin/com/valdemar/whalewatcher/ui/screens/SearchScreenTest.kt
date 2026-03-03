package com.valdemar.whalewatcher.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.valdemar.whalewatcher.data.network.DockerSearchResult
import com.valdemar.whalewatcher.ui.SearchUiState
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SearchScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `search bar is displayed and accepts input`() {
        val uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
        val searchQuery = MutableStateFlow("")

        composeTestRule.setContent {
            SearchScreen(
                uiState = uiState.value,
                searchQuery = searchQuery.value,
                onQueryChange = { searchQuery.value = it },
                onLoadNextPage = {},
            )
        }

        composeTestRule.onNodeWithText("Search for an image...").assertIsDisplayed()

        // This will fail initially because the text input may not be fully bound yet
        composeTestRule.onNodeWithText("Search for an image...").performTextInput("nginx")
    }

    @Test
    fun `loading state displays a loading indicator`() {
        composeTestRule.setContent {
            SearchScreen(
                uiState = SearchUiState.Loading,
                searchQuery = "",
                onQueryChange = {},
                onLoadNextPage = {},
            )
        }

        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun `empty state displays empty message`() {
        composeTestRule.setContent {
            SearchScreen(
                uiState = SearchUiState.Empty,
                searchQuery = "unknown_image",
                onQueryChange = {},
                onLoadNextPage = {},
            )
        }

        composeTestRule.onNodeWithText("No results found").assertIsDisplayed()
    }

    @Test
    fun `success state displays list of images and total count`() {
        val results =
            listOf(
                DockerSearchResult(
                    repoName = "library/nginx",
                    shortDescription = "Nginx",
                    starCount = 100L,
                    pullCount = 500L,
                ),
                DockerSearchResult(
                    repoName = "library/ubuntu",
                    shortDescription = "Ubuntu",
                    starCount = 200L,
                    pullCount = 1000L,
                ),
            )
        composeTestRule.setContent {
            SearchScreen(
                uiState = SearchUiState.Success(results = results, totalCount = 1500),
                searchQuery = "linux",
                onQueryChange = {},
                onLoadNextPage = {},
            )
        }

        // Test total count
        composeTestRule.onNodeWithText("Total found: 1500").assertIsDisplayed()

        // Test items
        composeTestRule.onNodeWithText("library/nginx").assertIsDisplayed()
        composeTestRule.onNodeWithText("library/ubuntu").assertIsDisplayed()
    }

    @Test
    fun `success state result item click triggers navigation callback`() {
        val results = listOf(
            DockerSearchResult(repoName = "library/nginx", shortDescription = "Nginx")
        )
        
        var navigatedNamespace = ""
        var navigatedName = ""

        composeTestRule.setContent {
            SearchScreen(
                uiState = SearchUiState.Success(results = results, totalCount = 1),
                searchQuery = "nginx",
                onQueryChange = {},
                onLoadNextPage = {},
                onNavigateToImage = { namespace, name ->
                    navigatedNamespace = namespace
                    navigatedName = name
                }
            )
        }

        composeTestRule.onNodeWithText("library/nginx").performClick()
        
        assert(navigatedNamespace == "library")
        assert(navigatedName == "nginx")
    }
}
